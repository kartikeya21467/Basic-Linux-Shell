#include<stdio.h>
#include<unistd.h>
#include<string.h>
#include<stdlib.h>
#include<sys/types.h>
#include<dirent.h>

int main(int argc , char **args){
    // printf("%s\n","Hello in new file");
    // printf("%d\n",argc);
    // printf("%s\n",args[0]);
    // printf("%s\n",args[1]);
    // printf("%s\n",args[2]);
    // printf("%s\n",args[3]);
    // printf("%s\n","can i get a hiiiyaaaa");
    struct dirent *d;
    DIR *dir = opendir(args[2]);
    if(dir==NULL){
        printf("%s\n","Error finding or reading directory");
        return -1;
    }
    int nline=0;
    // printf("%s\n","Goig in while loop");
    while((d=readdir(dir))!=NULL){
        // printf("%s\n","entered while loop");
        if(argc==1 || !strcmp(args[1],".") || !strcmp(args[1],"-A")){
            if(!strcmp(d->d_name,".") || !strcmp(d->d_name,"..")){
                continue;
            }
            printf("%s\t",d->d_name);
            nline=1;
        }
        if(!strcmp(args[1],"-a")){
            printf("%s\t",d->d_name);
            nline=1;
        }
        // else if(!strcmp(args[0],"-A") || !strcmp(args[0]," ")){
        //     if(!strcmp(d->d_name,".") || !strcmp(d->d_name,"..")){
        //         continue;
        //     }
        //     printf("%s\t",d->d_name);
        // }
        else if(!strcmp(args[1],"-1")){
            printf("%s\n",d->d_name);
        }
        // else{
        //     // printf("%s\n","blank");
        //     if(!strcmp(d->d_name,".") || !strcmp(d->d_name,"..")){
        //         continue;
        //     }
        //     printf("%s\t",d->d_name);
        //     nline=1;
        // }
    }
    if(nline==1) printf("\n");
    closedir(dir);
    return 0;

}