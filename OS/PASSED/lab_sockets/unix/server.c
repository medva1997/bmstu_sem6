#include <sys/types.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <signal.h>

#define SOCK_NAME "my_sock_unix"
#define SIZE 256
int sock;

void recv_msg(int sock) {
	char buffer[SIZE];
	int size = recv(sock, buffer, sizeof(buffer), 0);
	if (size < 0) {
		perror("server: Can't recieve data!\n");
		close(sock);
		remove(SOCK_NAME);
		exit(3);
	}
	buffer[size] = 0;
	printf("%s\n", buffer);
}

void sigint_handler(int signum)	//удаление файла
{
    close(sock);
    remove(SOCK_NAME);
    exit(1);
}

int main(void) {
	struct sockaddr_un addr; //стректура с локальным адресом (хранится имя файла)
	
		//AF-отпределяет сокеты локаьной свзи процесов
	sock = socket(AF_UNIX, SOCK_DGRAM, 0); //системный вызов(family, тип, протокол по умолчанию от типа, тут udp) 
						//возвращает дескриптор сокета
	if (sock < 0) {
		perror("server: Can't open socket!\n");
		exit(1);
	}
	signal(SIGINT, sigint_handler);// cntl+c

	addr.sun_family = AF_UNIX;
	strcpy(addr.sun_path, SOCK_NAME);

	// связвывает сокет с адресом(с файлом)
	if (bind(sock, (struct sockaddr *)&addr, sizeof(struct sockaddr)) < 0) {
		perror("server: Can't bind name to socket!\n");
		close(sock);
		remove(SOCK_NAME);
		exit(2);
	}
	printf("socket: %s\n", SOCK_NAME);

	for(;;) {
		recv_msg(sock);
	}

	close(sock);
	remove(SOCK_NAME);
	return 0;
}
