#include <stdio.h>
#include <stdlib.h>
#include "minesweeper.h"
#include <string.h>
///programul crapa daca dau ex AA2
int main()
{
///==========================================Citirea matricii==========================================
    int h, w, nr_bombs;
    printf("Linia, coloana si Numarul de bombe: ");
    scanf("%d %d %d", &h, &w, &nr_bombs);
    char t[h][w], **s;
    create(nr_bombs, h, w, t);
    s=init_state(h, w);
///==============================Citirea comenzilor====================================================
    play(h, w, s, t, nr_bombs);
    return 0;
}

