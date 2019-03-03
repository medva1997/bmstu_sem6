#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/init.h>
#include <linux/fs.h>
#include <linux/time.h>

MODULE_LICENSE("GPL");
MODULE_DESCRIPTION("My virtual filesystem");
MODULE_AUTHOR("Medvedev A.");

#define MY_VFS_MAGIC_NUMBER 0x13131313

static void my_vfs_put_super(struct super_block *sb) {
	printk(KERN_DEBUG "my_vfs super block destroyed!\n");
}

static struct super_operations const my_vfs_super_ops = {
	.put_super = my_vfs_put_super,
	.statfs = simple_statfs,
	.drop_inode = generic_delete_inode,
};

static struct inode *my_vfs_make_inode(struct super_block *sb, int mode) {
	struct inode *ret = new_inode(sb);

	if (ret) {
		inode_init_owner(ret, NULL, mode);
		ret->i_size = PAGE_SIZE;
		ret->i_atime = ret->i_mtime = ret->i_ctime = current_time(ret);
	}

	return ret;
}

static int my_vfs_fill_sb(struct super_block *sb, void *data, int silent) {
	struct inode *root = NULL;
	sb->s_blocksize = PAGE_SIZE;
	sb->s_blocksize_bits = PAGE_SHIFT;
	sb->s_magic = MY_VFS_MAGIC_NUMBER;
	sb->s_op = &my_vfs_super_ops;

	root = my_vfs_make_inode(sb, S_IFDIR | 0755);//создание инода коневого католога
	if (!root) {
		printk(KERN_ERR "my_vfs inode allocation failed!\n");
		return -ENOMEM;
	}
	root->i_op = &simple_dir_inode_operations;
	root->i_fop = &simple_dir_operations;

	sb->s_root = d_make_root(root);
	if (!sb->s_root) {
		printk(KERN_ERR "my_vfs root creation failed!\n");
		iput(root);
		return -ENOMEM;
	}
	return 0;
}

static struct dentry *my_vfs_mount(struct file_system_type *type, 
							int flags, char const *dev, void *data) {
	struct dentry *const entry = mount_bdev(type, flags, dev, data, my_vfs_fill_sb);
	if (IS_ERR(entry))
		printk(KERN_ERR "my_vfs mounting FAILED!\n");
	else
		printk(KERN_DEBUG "my_vfs mounted!\n");
	return entry;
}

static struct file_system_type my_vfs_type = {
.owner = THIS_MODULE,
.name = "my_vfs",
.mount = my_vfs_mount,
.kill_sb = kill_block_super,
};

static int __init my_vfs_init(void) {
	int ret = register_filesystem(&my_vfs_type);
	if (ret != 0) {
		printk(KERN_ERR "my_vfs: can't register filesystem!\n");
		return ret;
	}

	printk(KERN_DEBUG "my_vfs module loaded!\n");
	return 0;
}

static void __exit my_vfs_exit(void) {
	int ret = unregister_filesystem(&my_vfs_type);
	if (ret != 0)
		printk(KERN_ERR "my_vfs: can't unregister filesystem!\n");

	printk(KERN_DEBUG "my_vfs module unloaded!\n");
}

module_init(my_vfs_init);
module_exit(my_vfs_exit);


