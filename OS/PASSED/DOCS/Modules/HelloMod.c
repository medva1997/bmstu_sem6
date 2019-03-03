#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/init.h>

MODULE_LICENSE("GPL");
MODULE_AUTHOR("Salem");
MODULE_DESCRIPTION("HelloBye Module");

static int __init mod_init(void)
{	
	printk(KERN_INFO "Hello world!");
	return 0;

}

static void __exit mod_exit(void)
{
	printk(KERN_INFO "Good bye");
}

module_init(mod_init);
module_exit(mod_exit);

