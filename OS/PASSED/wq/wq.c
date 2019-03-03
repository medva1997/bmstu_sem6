#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/init.h>
#include <linux/interrupt.h>
#include <linux/workqueue.h>

struct workqueue_struct *wq;
struct work_struct *work;
int dev_id, irq = 1;

void wq_function(struct work_struct *work)
{
  printk(KERN_INFO "wq data: %d func %p", work->data, work->func);
  return;
}

static irqreturn_t my_interrupt(int irq, void *dev_id)
{
  queue_work(wq, work);

  return IRQ_HANDLED;
}

static int __init module_wq_init(void)
{
  int ret;

  wq = create_workqueue("wq");

  work = vmalloc(sizeof(struct work_struct));
  INIT_WORK(work, wq_function);

  if (!(ret = request_irq(irq, my_interrupt, IRQF_SHARED, "my_interrupt", &dev_id)))
    printk(KERN_INFO "module is loaded.\n");
  return ret;
}

static void __exit module_wq_exit(void)
{
  destroy_workqueue(wq);
  vfree(work);
  free_irq(irq, &dev_id);
  printk(KERN_INFO "module is unloaded.\n");
  return;
}

MODULE_LICENSE("GPL v2");
MODULE_AUTHOR("Anisimov Nikita");

module_init(module_wq_init);
module_exit(module_wq_exit);
