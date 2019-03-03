#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/init.h>

MODULE_LICENSE("GPL");
MODULE_AUTHOR("Salem");

extern char* ret_choice (int num);
extern int ret_rand(void);

extern char* ext_string;
extern int export_int;

static int __init mod3_init(void)
{

	printk(KERN_INFO "Mod3 init.");
	printk(KERN_INFO "Randomizer result %d \n", ret_rand());
	printk(KERN_INFO "What choice was made? %s \n", ret_choice(0));
	printk(KERN_INFO "What choice was made? %s \n", ret_choice(-1));
	printk(KERN_INFO "What choice was made? %s \n", ret_choice(1));
	
	printk(KERN_INFO "Exported int from module1 = %d",export_int);
	printk(KERN_INFO "Exported string from module1 = %s",ext_string);

	printk(KERN_INFO "HERE GOES THE ERROR IN MOD3");

	return -1;


}

static void __exit mod3_exit(void)
{
	printk(KERN_INFO "Mod3 exit.");
}

module_init(mod3_init);
module_exit(mod3_exit);
