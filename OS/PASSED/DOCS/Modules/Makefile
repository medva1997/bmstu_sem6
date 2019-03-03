CONFIG_MODULE_SIG=n

ifneq ($(KERNELRELEASE),)
	obj-m := HelloMod.o
else
	CURRENT = $(shell uname -r)
	KDIR = /lib/modules/$(CURRENT)/build
	PWD = $(shell pwd)

default:
	$(MAKE) -C $(KDIR) M=$(PWD) modules

clean:
	
	rm *.ko
	rm *.o
	rm *.mod.c
	rm *.symvers
	rm *.order

endif
