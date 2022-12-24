#include<stdio.h>
#include<unistd.h>
#include<string.h>
#include<stdlib.h>
#include<sys/types.h>
#include<sys/stat.h>
#include<dirent.h>

#define TOK_DELIM " /\t\r\n\a"

int main(int argc, char **args){
    if(!strcmp(args[1],".")){
        int check = mkdir(args[2],0777);
        if(check==1){
            printf("%s\n","Unable to create directory");
        }

    }
    else if(!strcmp(args[1],"-v")){
        int check = mkdir(args[2],0777);
        if(check==0){
            printf("%s \'%s\'\n","Created Directory",args[2]);
        }else{
            printf("%s\n","Unable to create directory");
        }
    }
    else if(!strcmp(args[1],"-p")){
        int count=0;
        
        count++;
        
        char **tokens = malloc(100 * sizeof(char *));
        
        int pos=0;
        const char s[2]=" ";
        char *token = strtok(args[2],TOK_DELIM);
        // printf("%s\n","Hello");
        while(token!=NULL){
            // printf("%s\n",token);
            tokens[pos++]=token;
            token=strtok(NULL,TOK_DELIM);
        }
        
        for(int i=0;i<pos;i++){
            int ch = chdir(tokens[i]);
            if(ch!=0){
                int check = mkdir(tokens[i],0777);
                if(check==0){
                    int c = chdir(tokens[i]);
                    if(c!=0){
                        printf("%s\n","Failed");
                        return 0;
                    }
                }else{
                    printf("%s\n","Failed");
                    return 0;
                }
            }
        }
        return 0;

    }
    return 0;
}
