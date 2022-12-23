#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<time.h>


int main(int argc, char **args){
    time_t now = time(NULL);
    char s[100];
    if(!strcmp(args[1],"-u")){
        struct tm *utctime = gmtime(&now);
        strftime(s,100,"%A %d %B %Y %l:%M:%S %p UTC",utctime);
        printf("%s\n",s);
        return 0;
    }
    else if(!strcmp(args[1],"-R")){
        struct tm *tm = localtime(&now);
        strftime(s,100,"%a, %d %b %Y %H:%M:%S %z",tm);
        printf("%s\n",s);
        return 0;
    }
    else{
        struct tm *tm = localtime(&now);
        strftime(s,100,"%A %d %B %Y %l:%M:%S %p IST",tm);
        printf("%s\n",s);
        return 0;
    }
}