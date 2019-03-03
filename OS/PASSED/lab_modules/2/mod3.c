/*
Модуль md3 демонстрирует сценарий некорректного завершения установки модуля, и
возможность использования загружаемого модуля в качестве функции выполняемой в
пространстве ядре.
Процедура инициализации этого загружаемого модуля должна возвращать ненулевое
значение и выводить в системный журнал данные и возвращаемые значения
экспортированных модулем md1 процедур (аналогично md2).
Данный модуль включен в работу для проработки вопросов, связанных с отладкой
модулей ядра.
 */

#include <linux/init.h>
#include <linux/kernel.h>
#include <linux/module.h>

MODULE_LICENSE("GPL");
MODULE_AUTHOR("Medvedev A");
MODULE_DESCRIPTION("Task 2. Module 3");

extern char* export_str;
extern int export_int;
extern char* ret_is_num_poz_neg(int num);
extern int ret_random_num(void);

static int __init mod3_init(void)
{	
	printk(KERN_INFO "Module md3 init!\n");

	printk(KERN_INFO "Result of 'ret_is_num_poz_neg()': %s\n", ret_is_num_poz_neg(-2));
	printk(KERN_INFO "Result of 'ret_is_num_poz_neg()': %s\n", ret_is_num_poz_neg(0));
	printk(KERN_INFO "Result of 'ret_is_num_poz_neg()': %s\n", ret_is_num_poz_neg(5));

	printk(KERN_INFO "Result if 'ret_random_num()': %d\n", ret_random_num());

	printk(KERN_INFO "Exported string from md1: %s\n", export_str);
	printk(KERN_INFO "Exported int from md1: %d\n", export_int);

	printk(KERN_INFO "Error in Module 3!\n");
	
	return -1;

}

static void __exit mod3_exit(void)
{
	printk(KERN_INFO "Module md3 exit!\n");
}

module_init(mod3_init);
module_exit(mod3_exit);
