/*
Реализовать загружаемый модуль ядра, который при загрузке записывает в системный
журнал сообщение “Hello world!”, а при выгрузке “Good by”. Модуль должен собираться
при помощи Make-файла.
*/

#include <linux/init.h>
#include <linux/kernel.h>
#include <linux/module.h>


MODULE_LICENSE("GPL");
MODULE_AUTHOR("Medvedev Alexey");
MODULE_DESCRIPTION("Module Printing Hello & Goodbye Messages");

static int __init my_module_init(void) {
	printk("Hello world!\n");
	return 0;
}

static void __exit my_module_exit(void) {
	printk("Goodbye!\n");
}

module_init(my_module_init);
module_exit(my_module_exit);
