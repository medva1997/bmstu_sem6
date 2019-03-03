#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/init.h>
#include <linux/interrupt.h>
#include <linux/sched.h>

MODULE_AUTHOR("Gorokhova I.");
MODULE_LICENSE("GPL v2");

int irq = 1;
int dev_id, scancode;
struct tasklet_struct *tasklet;           


#define KBD_DATA_REG 0x60
#define kbd_read_input() inb(KBD_DATA_REG)

static irqreturn_t irq_handler(int irq, void *dev_id) {
	tasklet_schedule(tasklet);
	return IRQ_HANDLED;
}

void tasklet_func(unsigned long data) {
	scancode = kbd_read_input();
	if (scancode < 103) {
		printk(KERN_INFO "tasklet: state: %ld", tasklet->state);
		printk(KERN_INFO "tasklet_lab: Keycode %d\n", scancode);
	}
}

static int __init load_module(void) {
	int res = request_irq(irq, irq_handler, IRQF_SHARED, "tasklet_lab", &dev_id);
	if (result < 0) {
		printk(KERN_ERR "tasklet_lab: Couldn't register interrupt handler!\n");
		return res;
	}

	tasklet = vmalloc(sizeof(struct tasklet_struct));
	tasklet_init(tasklet, tasklet_func, 0);

	printk(KERN_INFO "tasklet_lab: Module loaded!\n");
	return 0;
}

static void __exit exit_module(void) {
	free_irq(irq, &dev_id);
	tasklet_kill(tasklet);
	vfree(tasklet);
	printk(KERN_INFO "tasklet_lab: Module unloaded!\n");
}

module_init(load_module);
module_exit(exit_module);
