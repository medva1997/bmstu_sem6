#include <sys/types.h>
#include <sys/socket.h>
#include <sys/time.h> 
#include <netinet/in.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <fcntl.h>

#define SOCKET_PORT 9797
#define BUFFER_SIZE 256
#define LISTEN_QUEUE_SIZE 256

#define CLIENTS 5

void recieve_data(int clients[], int i) {
	char buffer[BUFFER_SIZE];
	int read = recv(clients[i], buffer, BUFFER_SIZE, 0);
	if (read <= 0) {
		close(clients[i]);
		clients[i] = 0;
	}
	else {
		buffer[read] = 0;
		printf("%s\n", buffer);
	}
}

int max(int listener, int arr[], int count) {
	int res = listener;
	for (int i = 0; i < count; i++) 
		if (arr[i] > res)
			res = arr[i];

	return res;
}

int insert(int s, int clients[], int count) {
	for (int i = 0; i < count; i++) 
		if (clients[i] == 0) {
			clients[i] = s;
			return 0;
		}
	return -1;
}

int main(void) {
	int sockL;
	struct sockaddr_in addr;
	int clients[CLIENTS] = {0};
	fd_set rset;

	sockL = socket(AF_INET, SOCK_STREAM, 0);
	if (sockL < 0) {
		perror("server: Can't open socket!\n");
		exit(1);
	}
	

	addr.sin_family = AF_INET;
	addr.sin_port = htons(SOCKET_PORT);
	addr.sin_addr.s_addr = INADDR_ANY;

	if (bind(sockL, (struct sockaddr *)&addr, sizeof(addr)) < 0) {
		perror("server: Can't bind to address!\n");
		exit(2);
	}

	
	listen(sockL, LISTEN_QUEUE_SIZE);

	

	while(1) {
		FD_ZERO(&rset); 
		FD_SET(sockL, &rset); 
		for (int i = 0; i < CLIENTS; i++)
			if (clients[i] != 0)
				FD_SET(clients[i], &rset);


		int mx = max(sockL, clients, CLIENTS);

		
		if (select(mx+1, &rset, NULL, NULL, NULL) <= 0) {
			perror("Can`t select\n");
			exit(3);
		}

		if (FD_ISSET(sockL, &rset)) {
			
			int sock = accept(sockL, NULL, NULL);
			if (sock < 0) {
				perror("server:Can't accept \n");
				exit(4);
			}
				
						
			if (insert(sock, clients, CLIENTS) < 0){
				perror("server: Too many clients!\n");
				exit(5);
			}
		}
		for (int i = 0; i < CLIENTS; i++) {
			if (FD_ISSET(clients[i], &rset))
				recieve_data(clients, i);
		}
	}
	return 0;
}
