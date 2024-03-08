#ifndef MINESWEEPER_H_INCLUDED
#define MINESWEEPER_H_INCLUDED

void print(int h, int w, char t[h][w]);
void create(int nr_bombs, int h, int w, char t[h][w]);
char** init_state(int h, int w);
int discover(int i, int j, int h, int w, char t[h][w], char** s);
void mark(int i, int j, int h, int w, char** s);
void player_view(int h, int w, char t[h][w], char** s);

typedef struct{
    char status;
    char column;
    int rows;
}command;
void spacing(char *x);
int extract(char *x, command vector[]);

#endif // MINESWEEPER_H_INCLUDED
