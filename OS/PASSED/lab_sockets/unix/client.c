#include <sys/types.h>
#include <sys/socket.h> 
#include <sys/un.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#define SOCK_NAME "my_sock_unix"

int main(int argc, char *argv[])
{
    int sock;
    struct sockaddr_un addr;

    sock = socket(AF_UNIX, SOCK_DGRAM, 0); 
    if (sock < 0)
    {
        perror("client: Can't open socket\n");
        exit(1);
    }

    addr.sun_family = AF_UNIX;
    strcpy(addr.sun_path, SOCK_NAME);

    //int sendto(int sockfd, const void *msg, int len, unsigned int flags,
                                //const struct sockaddr *to, int tolen);
    sendto(sock, argv[1], sizeof(argv[1]), 0, (struct sockaddr *)&addr, sizeof(addr));

    close(sock);
    return 0;
}
