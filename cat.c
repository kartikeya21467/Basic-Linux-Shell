#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<dirent.h>


int main(int argc, char **args){
    
	
    // printf("%s\n",args[2]);
    // printf("%s\n",args[3]);
    
    for(int i=2;i<argc;i++){
        char ch;

        DIR* dir = opendir(args[i]);
        if(dir){
            printf("%s: %s\n",args[i],"is a directory");
            closedir(dir);
            continue;
        }

        FILE *ptr = fopen(args[i], "r");

        if (NULL == ptr) {
            printf("Error file can't be opened \n");
            continue;
        }
        if(!strcmp(args[1],"-E")){
            ch=fgetc(ptr);
            while (!feof(ptr)) {
                
                if(ch=='\n'){
                    printf("%c",'$');
                }
                printf("%c", ch);
                ch = fgetc(ptr);
            }
            // if(ch!='\n'){
            //     printf("%c",'$');
            //     printf("\n");
            // } 
        }
        if(!strcmp(args[1],".")){
            ch=fgetc(ptr);
            while (!feof(ptr)) {
                
                printf("%c", ch);
                ch = fgetc(ptr);
            }
            // if(ch!='\n') printf("\n");
        }
        int count=1;
        if(!strcmp(args[1],"-n")){
            if(!feof(ptr)) printf("\t%d ",count++);
            int check=0;
            ch=fgetc(ptr);
            while (!feof(ptr)) {
                check=0;
                if(ch=='\n'){
                    check=1;
                }
                printf("%c", ch);
                ch = fgetc(ptr);
                if(check==1 && !feof(ptr)) printf("\t%d ",count++);
                
            }
            // if(ch!='\n') printf("\n");
        }

    }
	

}