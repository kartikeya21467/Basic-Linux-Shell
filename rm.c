#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<unistd.h>

int main(int argc , char ** args){
    if(!strcmp(args[1],"-d")){
        char s[100];
        sprintf(s,"rmdir %s",args[2]);
        int res = system(s);
        if(res!=0){
            printf("%s\n","Failed");
        }
        return 0;
    }
    if(!strcmp(args[1],"-v")){

        int res = unlink(args[2]);
        if(res==0){
            printf("Removed %s\n",args[2]);
        }else{
            printf("%s\n","Failed");
        }
        return 0;
    }
    if(!strcmp(args[1],".")){
        int res = unlink(args[2]);
        if(res!=0){
            printf("%s\n","Failed");
        }
        return 0;
    }
}