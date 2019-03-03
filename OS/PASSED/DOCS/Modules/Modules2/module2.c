#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/init.h>

MODULE_LICENSE("GPL");
MODULE_AUTHOR("Salem");

extern char* ext_string;
extern int export_int;

extern char* ret_choice (int num);
extern int ret_rand(void);



static int __init mod2_init(void)
{
	

	printk(KERN_INFO "Mod2 init.");
	printk(KERN_INFO "Randomizer result %d \n", ret_rand());
	printk(KERN_INFO "What choice was made? %s \n", ret_choice(0));
	printk(KERN_INFO "What choice was made? %s \n", ret_choice(-1));
	printk(KERN_INFO "What choice was made? %s \n", ret_choice(1));
	
	printk(KERN_INFO "Exported string from module1 = %s",ext_string);
	printk(KERN_INFO "Exported int from module1 = %d",export_int);


	return 0;


}

static void __exit mod2_exit(void)
{
	printk(KERN_INFO "Mod2 exit.");
}

module_init(mod2_init);
module_exit(mod2_exit);
