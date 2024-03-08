#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "minesweeper.h"
#include <ctype.h>

int dx[]= {-1, -1, -1, 0, 1, 1, 1, 0};
int dy[]= {-1, 0, 1, 1, 1, 0, -1, -1};

///==================================extract====================================
int extract(char *str, command vector[]){
    int count=0;
    char * p = strtok(str , " ");
    int n;
    while(p != NULL){
        int ok[2]={1,1}, position=0;
        n=strlen(p);
        if(p[position]=='!')
            vector[count].status='!';
        else{
            vector[count].status='.';
            position--;
            }
        position++;
        if((p[position]>='A' && p[position]<='Z') || (p[position]>='a' && p[position]<='z'))
                vector[count].column=p[position];
            else
                ok[0]=0;
        position++;
        int number=0;
        while(position<n){
            if(p[position]>='0' && p[position]<='9'){
                number=number*10+(p[position]-'0');
                position++;
            }
            else ok[1]=0;
        }
        vector[count].rows=number;
        count=count+(ok[0]*ok[1]);
        p = strtok(NULL , " ");
    }
    return count;
}
///=====================alocare-dinamica============================
char** init_state(int h, int w){
    char **s;
    s=malloc(h*sizeof(char*));
    for(int i=0; i<h; i++)
        s[i]=malloc(w*sizeof(char));

    for(int i=0; i<h;i++)
        for(int j=0; j<w; j++)
            *(*(s+i)+j)='?';
    return s;
}
void create(int nr_bombs, int h, int w, char t[h][w]){
    for(int i=0; i<h; i++)
        for(int j=0; j<w; j++)
            t[i][j]='.';
///===================citire-bombe==================================
    int line, column;
    srand(time(NULL));
    for(int i=0; i<nr_bombs;i++){
        line= rand() % h;
        column= rand() % w;
        t[line][column]='X';
    }
///====================prelucreare==================================
    for(int i=0;i<h;i++)
        for(int j=0;j<w;j++){
            int counter=0;
            if(t[i][j]!='X'){
                for(int k=0; k<8; k++)
                    if(i+dx[k]>=0 && i+dx[k]<h && j+dy[k]>=0 && j+dy[k]<w && t[i+dx[k]][j+dy[k]]=='X')
                        counter++;
                if(counter!=0)
                    t[i][j]=counter+'0';
            }
    }
}
///============================================discover=========================================
int discover(int i, int j, int h, int w, char t[h][w], char** s)
{
    if(i<0 || i>=h || j<0 || j>=w)
            return -2;
    if(t[i][j]=='X')
        return -1;
    if(s[i][j]=='.')
        return 0;
        if(t[i][j]!='.' && t[i][j]!='X'){
            s[i][j]='.';
            return 1;
        }
    else if(t[i][j]=='.'){
        s[i][j]='.';
        for(int k=0; k<8; k++)
            discover(i+dx[k], j+dy[k], h, w, t, s);
        return 2;
    }
}
///============================================spacing=======================
void spacing(char *x){
    int n=strlen(x), count=0;
    for(int i=0; i<n; i++)
        if(x[i]>='1' && x[i]<='9')
            count++;
    char *b=malloc((n+count)*sizeof(char));
    for(int i=0; i<n; i++)
        if(x[i]>='0' && x[i]<='9' )
            if (x[i+1]<'0' || x[i+1]>'9'){
                strcpy(b,x+i);
                strcpy(x+i+1,b);
                x[i+1]=' ';
                n++;
            }
    x[n]='\0';
}
///==========================================mark===================================================
void mark(int i, int j, int h, int w, char** s){
    if(!(i<0 || i>=h || j<0 || j>=w)){
        if(s[i][j]=='?'){
            s[i][j]='!';
        }
        else if(s[i][j]=='!'){
            s[i][j]='?';
        }
    }
}
void player_view(int h, int w, char t[h][w], char** s){
    for(int i=0; i<h; i++)
        for(int j=0;j<w; j++)
            if(s[i][j]=='.')
                s[i][j]=t[i][j];

    int cop=0;
    printf("      ");
    for(char i='A'; i<='Z'; i++)
        if(cop!=w){
            printf("%c",i);
            cop++;
        }
        else
            break;
    if(w>=27){
        cop=26;
    for(char i='a';i<='z';i++)
        if(cop!=w){
            printf("%c",i);
            cop++;
        }
        else
            break;
    }
//afisare-minesweeper=========================
    printf("\n");
    for(int i=0; i<h; i++){
        printf("%3d   ",i+1);
        for(int j=0; j<w; j++)
            printf("%c", s[i][j]);
        printf("   ");
        printf("%d\n", i+1);
    }
//afisare-litere==============================
    cop=0;
    printf("      ");
    for(char i='A'; i<='Z'; i++)
        if(cop!=w){
            printf("%c",i);
            cop++;
        }
        else
            break;
    if(w>=27){
        cop=26;
    for(char i='a';i<='z';i++)
        if(cop!=w){
            printf("%c",i);
            cop++;
        }
        else
            break;
    }
    printf("\n\n");
}
int statusWinning(int h, int w, char**s, int nr_bombs, int lose)
{
    if(lose==1)
        return -1;
    int counterdiscovered=0;
    for(int i=0; i<h; i++)
        for(int j=0; j<w; j++)
            if(s[i][j]=='.' || (s[i][j]>='1' && s[i][j]<='9'))
                counterdiscovered++;
    if(counterdiscovered==((h*w)-nr_bombs))
        return 1;
    else
        return 0;
}

void play(int h, int w, int **s, int **t, int nr_bombs){
    int count=0,maximum=0;
    command vector[100];
    char str[1000];
    do{
        player_view(h, w, t, s);
        printf("Introduceti comenzile: ");
        fflush(stdin);
        scanf("%[^\n]", &str);
        spacing(str);
        count=extract(str, vector);
        int j=0;
        for(int i=0; i<count; i++){
            if(vector[i].column>='A' && vector[i].column<='Z')
                j=vector[i].column-'A';
            else
                j=vector[i].column-'a'+26;
            if(vector[i].status=='!')
                    mark(vector[i].rows-1, j, h, w, s);
            else if(vector[i].status=='.')
                if(discover(vector[i].rows-1, j, h, w, t, s)==-1){
                    printf("Ati pierdut");
                    return 0;
                }
            else
                discover(vector[i].rows-1, j, h, w, t, s);
        }
    if(statusWinning(h, w, s, nr_bombs, 0)== 1){
        printf("Ati castigat!\n");
        player_view(h, w, t, s);
        return 0;
    }
    }while(count<100);
    printf("Numarul maxim de comenzi a fost atins");
}
