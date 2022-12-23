#include<stdio.h>
#include<unistd.h>
#include<string.h>
#include<stdlib.h>
#include<sys/wait.h>
#include<sys/types.h>
#include<dirent.h>
#include<pthread.h>



char *read_line(void)
{
  char *line = NULL;
  size_t size = 0;

  if (getline(&line, &size, stdin) == -1){
    if (feof(stdin)) {
      exit(EXIT_SUCCESS); 
    } else  {
      perror("readline");
      exit(EXIT_FAILURE);
    }
  }
//   printf("%s\n","HELLO");
  return line;

}

int internal_commands(char **cmdpieces, int pos){

    if(!strcmp(cmdpieces[0],"cd")){
        if((!strcmp(cmdpieces[2],"~") || !strcmp(cmdpieces[2],".")) && !strcmp(cmdpieces[1],".") ){
            int check = chdir(getenv("HOME"));
            if(check==0) return 1;
            else{
                printf("%s\n","Failed");
                return 1;
            }
        }
        
        if(strcmp(cmdpieces[2],".")){
            if(!strcmp(cmdpieces[1],".") || !strcmp(cmdpieces[1],".")){
                int check = chdir(cmdpieces[2]);
                if(check==0) return 1;
                else{
                    printf("%s\n","Failed");
                }
                return 1;
            }
            printf("%s\n","Not Supported");
            return 1;
        }
        printf("%s\n","Invalid");
        return 1;
    }

    // printf("%s\n","HELLO");
    if(!strcmp(cmdpieces[0],"echo")){
        if(pos>2){
            if(!strcmp(cmdpieces[1],".")){
                if(!strcmp(cmdpieces[2],".")){
                    printf("\n");
                    return 1;
                }
                printf("%s\n",cmdpieces[2]);
                return 1;
            }

            else if(!strcmp(cmdpieces[1],"-E")){
                if(!strcmp(cmdpieces[2],".")){
                    printf("\n");
                    return 1;
                }
                printf("%s\n",cmdpieces[2]);
                return 1;
            }

            else if(!strcmp(cmdpieces[1],"-n")){
                if(!strcmp(cmdpieces[2],".")){
                    printf("%s","");
                    return 1;
                }
                printf("%s",cmdpieces[2]);
                return 1;
            }
            else{
                printf("%s %s\n",cmdpieces[1],cmdpieces[2]);
                return 1;
            }

        }
        printf("%s\n","Invalid Operand");
        return 1;
    }
    // printf("%s\n","HELLO");
    if(!strcmp(cmdpieces[0],"pwd")){
        if(!strcmp(cmdpieces[3],"&t")){
            printf("%s\n","threading not allowed with pwd");
            return 1;
        }
        if(!strcmp(cmdpieces[2],".")){
            if(pos==1 || !strcmp(cmdpieces[1],".") || !strcmp(cmdpieces[1],"-L")){
                char pwd[150];
                getcwd(pwd,150);
                printf("%s\n",pwd);
                return 1;
            }else{
                printf("%s\n","Not supported");
                return 1;
            }
        }
        else{
            printf("%s\n","No Arguments supported with pwd");
            return 1;
        }

    }
    // printf("%s\n","HELLO");
    return -1;
}

char *cwd;
char *iwd;

void *systemcall(void *args){
    // printf("%s\n","HELOOO");
    system(args);
}


int external_commands(char **cmdpieces, int pos){

    if(!strcmp(cmdpieces[0],"ls")){
        if(!strcmp(cmdpieces[3],"&t")){
            if(!strcmp(cmdpieces[1],".") || !strcmp(cmdpieces[1],"-a") || !strcmp(cmdpieces[1],"-1") || !strcmp(cmdpieces[1],"-A")){
                char *s = malloc(100*sizeof(char));
                strcpy(s,"./ls ");
                strcat(s,cmdpieces[1]);
                strcat(s," ");
                strcat(s,cmdpieces[2]);
                // strcat(s," ");
                // strcat(s,cmdpieces[3]);
                    // system(s);
                // printf("%s\n",s);
                pthread_t t_id;
                int res =pthread_create(&t_id,NULL,&systemcall,s);
                if(res==0){
                    // printf("%s\n","res==0");
                        
                    pthread_join(t_id,NULL);

                }else{
                    printf("%s\n","Could not be created");
                }
                return -1;
            }else{
                printf("%s\n","Not Supported");
                return -1;
            }
            
        }
        else{
            if(!strcmp(cmdpieces[1],".") || !strcmp(cmdpieces[1],"-a") || !strcmp(cmdpieces[1],"-1") || !strcmp(cmdpieces[1],"-A")){
                    // printf("%s\n","Hello after fn comparison");
                    char *path = malloc(1024*sizeof(char));
                    // printf("%s\n","After malloc");
                    strcpy(path,iwd);
                    // printf("%s\n","After copy");
                    strcat(path,"/ls");
                    // printf("%s\n","After cat");

                    // printf("%s\n","Hello before forking");
                    pid_t rc = fork();
                    int st;
                    // printf("%s\n","Hello after forking");
                    if(rc<0){
                        printf("%s/n","Child process could not be created");
                    }
                    else if(rc==0){
                        // printf("%s\n","Hello before execl");
                        // printf("%s\n",path);
                        execl(path,cmdpieces[0],cmdpieces[1],cmdpieces[2],NULL);

                    }else{
                        waitpid(rc,&st,0);
                        return 1;
                    }
                    return -1;
                    // printf("%s\n","Hello after fork end");
                }else{
                    printf("%s\n","Not Supported");
                    return -1;
                }
        }
        return -1;
        
    }
    if(!strcmp(cmdpieces[0],"mkdir")){
        if(!strcmp(cmdpieces[3],"&t")){
            if(strcmp(cmdpieces[2],".")){
                if(!strcmp(cmdpieces[1],".") || !strcmp(cmdpieces[1],"-v") || !strcmp(cmdpieces[1],"-p")){
                    char *s = malloc(100*sizeof(char));
                    strcpy(s,"./mkdir ");
                    strcat(s,cmdpieces[1]);
                    strcat(s," ");
                    strcat(s,cmdpieces[2]);
                    
                    pthread_t t_id;
                    int res =pthread_create(&t_id,NULL,&systemcall,s);
                    if(res==0){
                        // printf("%s\n","res==0");
                            
                        pthread_join(t_id,NULL);

                    }else{
                        printf("%s\n","Could not be created");
                    }
                }else{
                    printf("%s\n","Not Supported");
                    return -1;
                }
            }else{
                printf("%s/n","Invalid");
                return -1;
            }
        }
        else{
            if(strcmp(cmdpieces[2],".")){
                if(!strcmp(cmdpieces[1],".") || !strcmp(cmdpieces[1],"-v") || !strcmp(cmdpieces[1],"-p")){
                    char *path = malloc(1024*sizeof(char));
                    strcpy(path,iwd);
                    strcat(path,"/mkdir");
                    pid_t rc = fork();
                    int st;
                    if(rc<0){
                        printf("%s\n","child process could not be created");
                    }
                    else if(rc==0){
                        execl(path,cmdpieces[0],cmdpieces[1],cmdpieces[2],NULL);
                    }
                    else{
                        waitpid(rc,&st,0);
                        return 1;
                    }
                }
                else{
                    printf("%s\n","Not Supported");
                    return -1;
                }
            }else{
                printf("%s\n","Invalid");
                return -1;
            }
        }
        return -1;
        
    }
    if(!strcmp(cmdpieces[0],"date")){
        if(!strcmp(cmdpieces[3],"&t")){
            if(!strcmp(cmdpieces[2],".")){
                if(!strcmp(cmdpieces[1],".") || !strcmp(cmdpieces[1],"-u") || !strcmp(cmdpieces[1],"-R")){
                    char *s = malloc(100*sizeof(char));
                    strcpy(s,"./date ");
                    strcat(s,cmdpieces[1]);
                    strcat(s," ");
                    strcat(s,cmdpieces[2]);
                    // strcat(s," ");
                    // strcat(s,cmdpieces[3]);
                        // system(s);
                    // printf("%s\n",s);
                    pthread_t t_id;
                    int res =pthread_create(&t_id,NULL,&systemcall,s);
                    if(res==0){
                        // printf("%s\n","res==0");
                            
                        pthread_join(t_id,NULL);

                    }else{
                        printf("%s\n","Could not be created");
                    }
                }else{
                    printf("%s\n","Not Supported");
                    return -1;
                }

            }else{
                printf("%s\n","Invalid");
                return -1;
            }
        }else{
            if(!strcmp(cmdpieces[2],".")){
                if(!strcmp(cmdpieces[1],".") || !strcmp(cmdpieces[1],"-u") || !strcmp(cmdpieces[1],"-R")){
                    char *path = malloc(1024*sizeof(char));
                    strcpy(path,iwd);
                    strcat(path,"/date");
                    pid_t rc = fork();
                    int st;
                    if(rc<0){
                        printf("%s\n","child process could not be created");
                    }
                    else if(rc==0){
                        execl(path,cmdpieces[0],cmdpieces[1],cmdpieces[2],NULL);
                    }
                    else{
                        waitpid(rc,&st,0);
                        return 1;
                    }
                }
                else{
                    printf("%s\n","Not Supported");
                    return -1;
                }

            }else{
                printf("%s\n","Invalid");
                return -1;
            }
        }
        return -1;
    }

    if(!strcmp(cmdpieces[0],"cat")){
        if(cmdpieces[3],"&t"){
            if(strcmp(cmdpieces[2],".")){
                if(!strcmp(cmdpieces[1],".") || !strcmp(cmdpieces[1],"-n") || !strcmp(cmdpieces[1],"-E")){
                    char *s = malloc(100*sizeof(char));
                    strcpy(s,"./cat ");
                    strcat(s,cmdpieces[1]);
                    strcat(s," ");
                    strcat(s,cmdpieces[2]);
                    // strcat(s," ");
                    // strcat(s,cmdpieces[3]);
                        // system(s);
                    // printf("%s\n",s);
                    pthread_t t_id;
                    int res =pthread_create(&t_id,NULL,&systemcall,s);
                    if(res==0){
                        // printf("%s\n","res==0");
                            
                        pthread_join(t_id,NULL);

                    }else{
                        printf("%s\n","Could not be created");
                    }
                }else{
                    printf("%s\n","Not Supported");
                    return -1;
                }
                
            }else{
                printf("%s\n","Invalid");
                return -1;
            }
        }
        else{
            if(strcmp(cmdpieces[2],".")){
                if(!strcmp(cmdpieces[1],".") || !strcmp(cmdpieces[1],"-n") || !strcmp(cmdpieces[1],"-E")){
                    char *path = malloc(1024*sizeof(char));
                    strcpy(path,iwd);
                    strcat(path,"/cat");
                    pid_t rc = fork();
                    int st;
                    if(rc<0){
                        printf("%s\n","child process could not be created");
                    }
                    else if(rc==0){
                        execl(path,cmdpieces[0],cmdpieces[1],cmdpieces[2],NULL);
                    }
                    else{
                        waitpid(rc,&st,0);
                        return 1;
                    }
                }
                else{
                    printf("%s\n","Not Supported");
                    return -1;
                }
                

            }else{
                printf("%s\n","Invalid");
                return -1;
            }
        }
        return -1;
    }
    
    if(!strcmp(cmdpieces[0],"rm")){
        if(!strcmp(cmdpieces[3],"&t")){
            if(strcmp(cmdpieces[2],".")){
                if(!strcmp(cmdpieces[1],".") || !strcmp(cmdpieces[1],"-v") || !strcmp(cmdpieces[1],"-d")){
                    char *s = malloc(100*sizeof(char));
                    strcpy(s,"./rm ");
                    strcat(s,cmdpieces[1]);
                    strcat(s," ");
                    strcat(s,cmdpieces[2]);
                    // strcat(s," ");
                    // strcat(s,cmdpieces[3]);
                        // system(s);
                    // printf("%s\n",s);
                    pthread_t t_id;
                    int res =pthread_create(&t_id,NULL,&systemcall,s);
                    if(res==0){
                        // printf("%s\n","res==0");
                            
                        pthread_join(t_id,NULL);

                    }else{
                        printf("%s\n","Could not be created");
                    }
                }else{
                    printf("%s\n","Not Supported");
                    return -1;
                }
                
            }else{
                printf("%s\n","Invalid");
                return -1;
            }
        }
        else{
            if(strcmp(cmdpieces[2],".")){
                if(!strcmp(cmdpieces[1],".") || !strcmp(cmdpieces[1],"-v") || !strcmp(cmdpieces[1],"-d")){
                    char *path = malloc(1024*sizeof(char));
                    strcpy(path,iwd);
                    strcat(path,"/rm");
                    pid_t rc = fork();
                    int st;
                    if(rc<0){
                        printf("%s\n","child process could not be created");
                    }
                    else if(rc==0){
                        execl(path,cmdpieces[0],cmdpieces[1],cmdpieces[2],NULL);
                    }
                    else{
                        waitpid(rc,&st,0);
                        return 1;
                    }
                }else{
                    printf("%s\n","Not Supported");
                    return -1;
                }
            }else{
                printf("%s\n","Invalid");
                return -1;
            }
        }
        return -1;
        
    }
    printf("%s: %s\n",cmdpieces[0],"command not found");
    return -1;
    

}

int exitnum = 0;
#define TOK_DELIM " \t\r\n\a"
#define TOK_DELIM2 " \n\0"

int processexecution(char *input){
    // printf("%s\n","HELLO");
    char **tokens = malloc(20 * sizeof(char *));
    int pos = 0;
    char *token = strtok(input,TOK_DELIM);
    if (token != NULL)
    {
        tokens[pos++] = token;
    }

    int checker=1;

    
    #define TOK_DELIM3 "\n\0"
    if(!strcmp(token,"echo")){
        token=strtok(NULL,TOK_DELIM);
        if(token!=NULL){
            if(token[0]=='-'){
                tokens[pos++]=token;
            }
            else{
                tokens[pos++]=".";
                checker=0;
            }
        }
        if(checker==1){
            token=strtok(NULL,TOK_DELIM3);
            if(token!=NULL){
                tokens[pos++]=token;
            }
        }else{
            char *token1 = strtok(NULL,TOK_DELIM3);
            if(token1!=NULL){
                char *temp = malloc(strlen(token)+strlen(token1)+2);
                strcpy(temp,token);
                strcat(temp," ");
                strcat(temp,token1);
                // printf("%s ", input);
                // printf(" %s\n", temp); // printing each token
                tokens[pos++]=temp;
            }else{
                tokens[pos++]=token;
            }
        }
    }else{
        token = strtok(NULL,TOK_DELIM);
        if(token!=NULL){
            if(token[0]=='-'){
                // printf("%s ", input);
                // printf(" %s\n", token); // printing each token
                tokens[pos++] = token;
            }else if(!strcmp(token,"&t")){
                tokens[pos++]=".";
                tokens[pos++]=".";
                tokens[pos++]=token;
                
            }else{
                tokens[pos++]=".";
                checker=0;
            }
            
        }
        
        if(checker==1){
            token=strtok(NULL,TOK_DELIM2);
            if(token!=NULL){
                // printf("%s ", input);
                // printf(" %s\n", token); // printing each token
                if(!strcmp(token,"&t")){
                    tokens[pos++]=".";
                    
                }
                tokens[pos++]=token;
            }
        }else{
            char *token1 = strtok(NULL,TOK_DELIM2);
            if(token1!=NULL){
                if(!strcmp(token1,"&t")){
                    tokens[pos++]=token;
                    tokens[pos++]=token1;
                }
                else{
                    char *temp = malloc(strlen(token)+strlen(token1)+2);
                    strcpy(temp,token);
                    strcat(temp," ");
                    strcat(temp,token1);
                    // printf("%s ", input);
                    // printf(" %s\n", temp); // printing each token
                    tokens[pos++]=temp;
                    checker=0;
                }
                
            }
            else{
                tokens[pos++]=token;
                checker=0;
            }
        }

        token = strtok(NULL,TOK_DELIM2);
        if(token!=NULL){
            tokens[pos++]=token;
            
        }
    }

    
    int j=pos;
    for(int i=0;i<4-j;i++){
        tokens[pos++]=".";
    }

    // for(int i=0;i<pos;i++){
    //     printf("%s\n",tokens[i]);
    // }
    // printf("%d\n",pos);
    // printf("%s\n","HELLO");
    if(!strcmp(tokens[0],"exit")){
        return -2;
    }
    int n = internal_commands(tokens,pos);
    // printf("%s\n","HELLO");
    if(n==-1){
        n = external_commands(tokens,pos);
    }
    return n;
    
}

int main(){

    char *input = malloc(1024*sizeof(char));
    int executed=0;
    cwd = malloc(1024*sizeof(char));
    iwd = malloc(1024*sizeof(char));
    getcwd(iwd,1024);
    while(1){
        
        getcwd(cwd,1024);
        printf("%s:~$ ",cwd);
        
        input = read_line();
        // printf("%s\n","HELLO");
        char *t = strtok(input,"\n\0");
        if(t!=NULL){
            executed=processexecution(t);
        }
        if(executed==-2){
            break;
        }

    }
}