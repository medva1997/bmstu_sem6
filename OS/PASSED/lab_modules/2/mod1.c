/*Модуль md1 демонстрирует возможность создания экспортируемых 
данных и функций.
Данный модуль ядра должен содержать:
Экспортируемые строковые (char *) и численные (int) данные.
Экспортируемые функции возвращающие строковые и числовые значения. */
#include <linux/init.h>
#include <linux/kernel.h>
#include <linux/module.h>
#include <linux/random.h>

MODULE_LICENSE("GPL");
MODULE_AUTHOR("Medvedev A");
MODULE_DESCRIPTION("Task 2. Module 1");

char* export_str = "";
int export_int = 0;

extern char* ret_is_num_poz_neg(int num) {
	//printk(KERN_INFO "Returns the answer about what this number is (md1)\n");
	
	if (num > 0)
	{
		export_str = "Pozitive number";
		return "This number is pozitive";
	}
	else if (num < 0) {
		export_str = "Negative number";
		return "This number is negative";
	}
	else {
		export_str = "Zero";
		return "This number is zero";
	}
}

extern int ret_random_num(void) {
	int num = get_random_int();
	export_int = num;
	return num;
}

EXPORT_SYMBOL(export_str);
EXPORT_SYMBOL(export_int);
EXPORT_SYMBOL(ret_is_num_poz_neg);
EXPORT_SYMBOL(ret_random_num);

static int __init mod1_init(void)
{	
	printk(KERN_INFO "Module md1 init!\n");
	return 0;

}

static void __exit mod1_exit(void)
{
	printk(KERN_INFO "Module md1 exit!\n");
}

module_init(mod1_init);
module_exit(mod1_exit);
