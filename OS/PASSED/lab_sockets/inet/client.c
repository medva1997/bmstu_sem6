#include <sys/types.h>
#include <sys/socket.h> 
#include <netinet/in.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>

#define SOCKET_PORT 9797
#define BUFFER_SIZE 256

int main(int argc, char** argv) {
	int sock;
	struct sockaddr_in addr;

	sock = socket(AF_INET, SOCK_STREAM, 0);
	if (sock < 0) {
		perror("client: Can't open socket!\n");
		exit(1);
	}

	addr.sin_family = AF_INET;
	addr.sin_port = htons(SOCKET_PORT);
	addr.sin_addr.s_addr = htonl(INADDR_LOOPBACK);

	
	if (connect(sock, (struct sockaddr *)&addr, sizeof(addr)) < 0) {
		perror("Can't connect!\n");
		exit(2);
	}

	char buf[BUFFER_SIZE];
	char nbuf[BUFFER_SIZE];
	printf("Input message: ");
	scanf("%s", buf);
	
	send(sock, buf, strlen(buf), 0);

	close(sock);
	exit(0);
}
