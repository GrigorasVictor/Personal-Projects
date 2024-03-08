#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

#define n 9
int matrix[n][n]={ { 3, 0, 0, 7, 9, 0, 6, 0, 0 },
                   { 0, 5, 0, 3, 2, 0, 0, 0, 0 },
                   { 9, 0, 0, 0, 8, 1, 0, 0, 0 },
                   { 0, 1, 0, 8, 0, 9, 0, 4, 0 },
                   { 0, 9, 6, 5, 4, 7, 0, 0, 0 },
                   { 4, 0, 5, 1, 0, 0, 7, 0, 0 },
                   { 0, 4, 3, 9, 0, 0, 8, 0, 7 },
                   { 1, 0, 0, 2, 7, 3, 4, 0, 0 },
                   { 0, 6, 7, 0, 5, 0, 0, 9, 3 } };
void separator(int mux){
    if(mux == 1) {
        for (int k = 0; k < 21; k++)
            printf("-");
        puts("");
    }
    else if(mux == 2){
        printf("| ");
    }
}
void print_matrix(){
    for(int i=0; i<n; i++) {
        if(i%3==0)
            separator(1);
        for (int j = 0; j < n; j++) {
            if (j % 3 == 0 && j>=3)
                separator(2);
            printf("%d ", matrix[i][j]);
        }
        puts("");
    }
    separator(1);
    puts("");
}

int howManyZeros(){
    int ans=0;
    for(int i=0; i<n; i++)
        for(int j=0; j<n; j++)
            if(matrix[i][j]==0)
                ans++;
    return ans;
}
bool is_valid(int i, int j, int number){
    ///verificam daca ii valid numarul pe linii si coloane
    for(int k=0; k<n; k++){
        if(matrix[k][j] == number || matrix[i][k] == number)
            return false;
    }
    ///verificam daca ii valid in matricea de 3x3
    int n1=i-i%3, m1=j-j%3;
    for(int k=n1; k<n1+3; k++)
        for(int l=m1; l<m1+3; l++)
            if(matrix[k][l] == number)
                return false;
    return true;
}
void resolve(int i, int j, int emptyCell, int *ans){
    if(emptyCell==0)
        *ans=1;
    if(*ans==1)
        return;
    if(i>=n)
        return;
    if(j>=n)
        resolve(i+1, 0, emptyCell, ans);
    if(matrix[i][j]!=0)
        resolve(i, j+1, emptyCell, ans);
else
    for(int number=1; number<=9 && *ans==0; number++)
        if(is_valid(i, j, number)==1){
            matrix[i][j] = number;
            resolve(i, j+1, emptyCell-1, ans);
            if(*ans==0)
                matrix[i][j]=0;
            else
                return;
        }
}
int main() {
    puts("Matrix before:");
    print_matrix();

    int *ans=calloc(1, sizeof(int)), k=howManyZeros();
    resolve(0, 0, k, ans);
    printf("Matrix after the backtracking and ");
    if(*ans)
        puts("it's solved!");
    else
        puts("it's not solved!");
    print_matrix();
    return 0;
}
