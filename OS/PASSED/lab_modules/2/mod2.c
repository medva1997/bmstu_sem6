/* 
Модуль md2 демонстрирует использование данных и функций экспортируемых первым
модулем (md1).
Данный модуль должен при загрузке:
Вызывать все экспортированные модулем md1 процедуры и вывести в системный
журнал возвращаемые ими значения с указанием имени вызванной процедуры.
Вывести в системный журнал все экспортированные модулем md1 данные.
*/

#include <linux/init.h>
#include <linux/kernel.h>
#include <linux/module.h>

MODULE_LICENSE("GPL");
MODULE_AUTHOR("Medvedev A");
MODULE_DESCRIPTION("Task 2. Module 2");

extern char* export_str;
extern int export_int;
extern char* ret_is_num_poz_neg(int num);
extern int ret_random_num(void);

static int __init mod2_init(void)
{	
	printk(KERN_INFO "Module md2 init!\n");

	printk(KERN_INFO "Result of 'ret_is_num_poz_neg()': %s\n", ret_is_num_poz_neg(-2));
	printk(KERN_INFO "Result of 'ret_is_num_poz_neg()': %s\n", ret_is_num_poz_neg(0));
	printk(KERN_INFO "Result of 'ret_is_num_poz_neg()': %s\n", ret_is_num_poz_neg(5));

	printk(KERN_INFO "Result if 'ret_random_num()': %d\n", ret_random_num());

	printk(KERN_INFO "Exported string from md1: %s\n", export_str);
	printk(KERN_INFO "Exported int from md1: %d\n", export_int);

	return 0;

}

static void __exit mod2_exit(void)
{
	printk(KERN_INFO "Module md2 exit!\n");
}

module_init(mod2_init);
module_exit(mod2_exit);
