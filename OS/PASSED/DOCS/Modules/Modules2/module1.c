#include <linux/random.h>
#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/init.h>


MODULE_LICENSE("GPL");
MODULE_AUTHOR("Salem");

#define NO_CHOICE 0
#define GOOD_CHOICE 1
#define BAD_CHOICE -1

char* ext_string = "No choice has been made";
int export_int = 1;

extern char* ret_choice (int num)
{
	printk( KERN_INFO "Returning chosen string. Module1");

	switch(num)
	{
		case NO_CHOICE:
			ext_string = "No choice ";
			return "No choice has been made";
		case GOOD_CHOICE:
			ext_string = "you made a good choice";
			return "Congrats, you made a good choice";
		case BAD_CHOICE:
			ext_string = "you've made a bad choice";
			return "Sorry, you've made a bad choice";
		default:
			ext_string = "Default case triggered";
			return "Default case triggered";
		


	}
}

extern int ret_rand(void)
{
	int ran = get_random_int(); 
	export_int = ran;
	return ran;

}


EXPORT_SYMBOL(ext_string);
EXPORT_SYMBOL(export_int);

EXPORT_SYMBOL(ret_choice);
EXPORT_SYMBOL(ret_rand);


static int __init mod_init(void)
{	
	printk(KERN_INFO "Mod1 running");
	return 0;

}

static void __exit mod_exit(void)
{
	printk(KERN_INFO "Mod1 exited");
}

module_init(mod_init);
module_exit(mod_exit);


