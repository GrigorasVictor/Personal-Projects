.586
.model flat, stdcall
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
includelib msvcrt.lib
extern exit: proc
extern malloc: proc
extern memset: proc
extern rand: proc
extern srand: proc
extern time: proc

includelib canvas.lib
extern BeginDrawing: proc
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
public start
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
.data
window_title DB "PacMan - Grigoras Victor Andrei",0
area_width EQU 1200
area_height EQU 800
area DD 0

counter DD 0 ; numara evenimentele de tip timer
score DD 0 ;

x DD 0 ;pentru matrice
y DD 30 ; pentru matrice

arg1 EQU 8
arg2 EQU 12
arg3 EQU 16
arg4 EQU 20

player_x DD 1
player_y DD 18
gura_deschisa DD 1
orientation DD 3
desire_orientation DD 3 ;pentru directia dorita de jucator

orientation_ghost1 DD 1
orientation_ghost2 DD 3
orientation_ghost3 DD 3

;3 ->dreapta
;1 ->stanga
;2 ->jos
;0 ->sus

ghost1_x DD 19
ghost1_y DD 10

ghost2_x DD 20
ghost2_y DD 10

ghost3_x DD 21
ghost3_y DD 10


symbol_width EQU 10
symbol_height EQU 20

block_width EQU 30
block_height EQU 20

players_width EQU 30
players_height EQU 30 

button_size EQU 40

button_w_x EQU 1090
button_w_y EQU 70

button_s_x EQU 1090
button_s_y EQU 130

button_a_x EQU 1040
button_a_y EQU 130

button_d_x EQU 1140
button_d_y EQU 130

game_matrix_height EQU 37
game_matrix_width EQU 40

game_map	DD 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0 
			DD 1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,1,0,0,0,0,0,0 
			DD 1,2,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,2,1,1,2,1,1,1,1,1,1,1,1,1,1,2,1,0,0,0,0,0,0 
			DD 1,2,1,0,0,0,0,0,0,1,2,1,0,0,0,0,0,1,2,1,1,2,1,0,0,0,0,0,0,0,0,1,2,1,0,0,0,0,0,0 
			DD 1,2,1,0,0,0,0,0,0,1,2,1,0,0,0,0,0,1,2,1,1,2,1,0,0,0,0,0,0,0,0,1,2,1,0,0,0,0,0,0 
			DD 1,2,1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,2,1,1,2,1,1,1,1,1,1,1,1,1,1,2,1,0,0,0,0,0,0 
			DD 1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,0,0,0,0,0,0 
			DD 1,0,1,1,1,1,1,1,1,1,2,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,2,1,1,1,1,0,0,0,0,0,0 
			DD 1,0,0,0,0,0,0,0,0,0,2,1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,1,2,1,0,0,0,0,0,0,0,0,0 
			DD 1,0,1,1,1,0,1,0,1,1,2,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,2,1,0,0,0,0,0,0,0,0,0 
			DD 1,0,3,0,1,0,1,0,1,1,2,1,0,0,0,0,0,0,0,4,5,6,0,0,0,0,0,0,1,2,1,0,0,0,0,0,0,0,0,0
			DD 1,0,1,0,1,0,3,0,1,1,2,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,2,1,0,0,0,0,0,0,0,0,0
			DD 1,0,1,0,1,1,1,0,1,1,2,1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,1,2,1,0,0,0,0,0,0,0,0,0
			DD 1,0,0,0,0,0,0,0,0,0,2,1,0,1,0,1,0,1,0,1,1,0,1,0,1,0,1,0,1,2,1,0,0,0,0,0,0,0,0,0
			DD 1,1,1,1,1,1,1,1,1,1,2,1,0,1,0,1,0,1,0,1,1,0,1,0,1,0,1,0,1,2,1,0,0,0,0,0,0,0,0,0
			DD 0,0,0,0,0,0,0,0,0,1,2,1,0,1,0,1,0,1,0,1,1,0,1,0,1,0,1,0,1,2,1,0,0,0,0,0,0,0,0,0
			DD 0,0,0,0,0,0,0,0,0,1,2,1,0,1,0,1,0,1,0,1,1,0,1,0,1,0,1,0,1,2,1,0,0,0,0,0,0,0,0,0
			DD 1,1,1,1,1,1,1,1,1,1,2,1,0,1,0,1,0,1,0,1,1,0,1,0,1,0,1,0,1,2,1,1,1,1,1,1,1,1,1,1
			DD 0,7,0,0,0,0,0,0,0,0,2,1,0,1,0,3,0,1,0,1,1,0,1,0,3,0,1,0,1,2,0,0,0,0,0,0,0,0,0,0
			DD 1,1,1,1,1,1,1,1,1,1,2,1,0,1,0,1,0,1,0,1,1,0,1,0,1,0,1,0,1,2,1,1,1,1,1,1,1,1,1,1
			DD 0,0,0,0,0,0,0,0,0,1,2,1,0,1,0,1,0,1,0,1,1,0,1,0,1,0,1,0,1,2,1,0,0,0,0,0,0,0,0,0
			DD 0,0,0,0,0,0,0,0,0,1,2,1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,1,2,1,0,0,0,0,0,0,0,0,0
			DD 1,1,1,1,1,1,1,1,1,1,2,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,2,1,1,1,1,1,1,1,1,1,1
			DD 1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1
			DD 1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1
			DD 1,2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,2,1
			DD 1,2,1,2,1,2,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1,2,1,2,1,1,1,1,1,1,1,1,1,1,2,1,2,1,2,1
			DD 1,2,1,2,1,2,2,2,2,2,2,2,2,2,2,2,2,1,2,1,1,2,1,2,2,2,2,2,2,2,2,2,2,1,2,1,2,1,2,1
			DD 1,2,1,2,1,2,1,2,1,1,1,1,1,1,1,1,2,1,2,1,1,2,1,2,1,1,1,1,1,1,1,1,2,1,2,1,2,1,2,1
			DD 1,2,1,2,1,2,1,2,1,0,0,0,0,0,0,1,2,1,2,1,1,2,1,2,1,0,0,0,0,0,0,1,2,1,2,1,2,1,2,1
			DD 1,2,1,2,1,2,1,2,1,0,0,0,0,0,0,1,2,1,2,1,1,2,1,2,1,0,0,0,0,0,0,1,2,1,2,1,2,1,2,1
			DD 1,2,1,2,1,2,1,2,1,0,0,0,0,0,0,1,2,1,2,1,1,2,1,2,1,0,0,0,0,0,0,1,2,1,2,1,2,1,2,1
			DD 1,2,1,2,1,2,1,2,1,1,1,1,1,1,1,1,2,1,2,1,1,2,1,2,1,1,1,1,1,1,1,1,2,1,2,1,2,1,2,1
			DD 1,2,1,2,1,2,1,2,2,2,2,2,2,2,2,2,2,1,2,1,1,2,1,2,2,2,2,2,2,2,2,2,2,1,2,1,2,1,2,1
			DD 1,2,1,2,1,2,1,1,1,1,1,1,1,1,1,1,2,1,2,1,1,2,1,1,1,1,1,1,1,1,1,1,2,1,2,1,2,1,2,1
			DD 1,2,2,2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,2,2,2,1
			DD 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1			

include digits.inc
include letters.inc
include pacman.inc
include structures.inc
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
.code
; procedura make_text afiseaza o litera sau o cifra la coordonatele date
; arg1 - simbolul de afisat (litera sau cifra)
; arg2 - pointer la vectorul de pixeli
; arg3 - pos_x
; arg4 - pos_y

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
line_horizontal macro x, y, len, color
local loop_line
	mov eax, y ; EAX =y
	mov ebx, area_width
	mul ebx ; EAX= y* area_width
	add eax, x ; EAX = (y* area_width + x)
	shl eax, 2 ; EAX = (y* area_width + x) * DDWORD
	add eax, area
	mov ecx, len ; cati pixeli sa fie de lunga

loop_line:	
	mov dword ptr[eax], color
	add eax, 4
	loop loop_line
endm

line_vertical macro x, y, len, color
local loop_line

	mov eax, y ; EAX =y
	mov ebx, area_width
	mul ebx ; EAX= y* area_width
	add eax, x ; EAX = (y* area_width + x)
	shl eax, 2 ; EAX = (y* area_width + x) * DDWORD
	add eax, area
	mov ecx, len; cati pixeli sa fie de lunga
	
loop_line:	
	mov dword ptr[eax], color
	add eax, area_width*4
	loop loop_line
endm
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
line_horizontal_big macro x, y, len, color, color_2
local loop_line

	mov eax, y ; EAX =y
	mov ebx, area_width
	mul ebx ; EAX= y* area_width
	add eax, x ; EAX = (y* area_width + x)
	shl eax, 2 ; EAX = (y* area_width + x) * DDWORD
	add eax, area
	mov ecx, len-1 ; cati pixeli sa fie de lunga
	
	mov dword ptr[eax], color_2
	mov dword ptr[eax+(area_width)*4], color_2
	mov dword ptr[eax+(area_width)*8], color_2
	mov dword ptr[eax+(area_width)*12], color_2
	mov dword ptr[eax+(area_width)*16], color_2
	mov dword ptr[eax+(area_width)*20], color_2
	mov dword ptr[eax+(area_width)*24], color_2
	mov dword ptr[eax+(area_width)*28], color_2	
	mov dword ptr[eax+(area_width)*32], color_2
	add eax, 4
	
loop_line:
	mov dword ptr[eax], color_2
	mov dword ptr[eax+(area_width)*4], color
	mov dword ptr[eax+(area_width)*8], color
	mov dword ptr[eax+(area_width)*12], color
	mov dword ptr[eax+(area_width)*16], color
	mov dword ptr[eax+(area_width)*20], color
	mov dword ptr[eax+(area_width)*24], color
	mov dword ptr[eax+(area_width)*28], color		
	mov dword ptr[eax+(area_width)*32], color_2
	add eax, 4
	loop loop_line
	
	mov dword ptr[eax], color_2
	mov dword ptr[eax+(area_width)*4], color_2
	mov dword ptr[eax+(area_width)*8], color_2
	mov dword ptr[eax+(area_width)*12], color_2
	mov dword ptr[eax+(area_width)*20], color_2
	mov dword ptr[eax+(area_width)*24], color_2
	mov dword ptr[eax+(area_width)*28], color_2	
	mov dword ptr[eax+(area_width)*32], color_2
endm
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
line_vertical_big macro x, y, len, color, color_2
local loop_line
	mov eax, y ; EAX =y
	mov ebx, area_width
	mul ebx ; EAX= y* area_width
	add eax, x ; EAX = (y* area_width + x)
	shl eax, 2 ; EAX = (y* area_width + x) * DDWORD
	add eax, area
	mov ecx, len-1 ; cati pixeli sa fie de lunga
	
	mov dword ptr[eax], color_2
	mov dword ptr[eax+4], color_2
	mov dword ptr[eax+8], color_2
	mov dword ptr[eax+12], color_2
	mov dword ptr[eax+16], color_2
	mov dword ptr[eax+20], color_2
	mov dword ptr[eax+24], color_2
	mov dword ptr[eax+28], color_2	
	mov dword ptr[eax+32], color_2
	add eax, 4*area_width
loop_line:
	mov dword ptr[eax], color_2
	mov dword ptr[eax+4], color
	mov dword ptr[eax+8], color
	mov dword ptr[eax+12], color
	mov dword ptr[eax+16], color
	mov dword ptr[eax+20], color
	mov dword ptr[eax+24], color
	mov dword ptr[eax+28], color		
	mov dword ptr[eax+32], color_2
	add eax, 4*area_width
	loop loop_line
	
	mov dword ptr[eax], color_2
	mov dword ptr[eax+4], color_2
	mov dword ptr[eax+8], color_2
	mov dword ptr[eax+12], color_2
	mov dword ptr[eax+16], color_2
	mov dword ptr[eax+20], color_2
	mov dword ptr[eax+24], color_2
	mov dword ptr[eax+28], color_2	
	mov dword ptr[eax+32], color_2
endm
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
square macro x, y, len, color
local loop_line

	mov eax, y ; EAX =y
	mov ebx, area_width
	mul ebx ; EAX= y* area_width
	add eax, x ; EAX = (y* area_width + x)
	shl eax, 2 ; EAX = (y* area_width + x) * DDWORD
	add eax, area
	mov ecx, len ; cati pixeli sa fie de lunga
loop_line:
	mov dword ptr[eax], color
	mov dword ptr[eax+(area_width+ecx)*4], color
	add eax, 4
	loop loop_line
endm
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
make_text proc
	push ebp
	mov ebp, esp
	pusha
	
	mov eax, [ebp+arg1] ; citim simbolul de afisat if(characher<'A' si character>'Z') afiseaza numar, daca nu litera	
	cmp eax, '<'
		je make_pacman
	cmp eax, 'A';									 
	jl make_digit
	cmp eax, 'Z'
	jg make_digit
	sub eax, 'A'
	lea esi, letters
	jmp draw_text
make_digit:
	cmp eax, '0'
	jl make_space
	cmp eax, '9'
	jg make_space
	sub eax, '0'
	lea esi, digits
	jmp draw_text
make_space:	
	mov eax, 26 ; de la 0 pana la 25 sunt litere, 26 e space
	lea esi, letters
	jmp draw_text
	
make_pacman:
	mov eax, 0
	lea esi, pacman
	
draw_text:
	mov ebx, symbol_width
	mul ebx
	mov ebx, symbol_height
	mul ebx
	add esi, eax
	mov ecx, symbol_height
	
bucla_simbol_linii:
	mov edi, [ebp+arg2] ; pointer la matricea de pixeli
	mov eax, [ebp+arg4] ; pointer la coord y
	add eax, symbol_height
	sub eax, ecx
	mov ebx, area_width
	mul ebx
	add eax, [ebp+arg3] ; pointer la coord x
	shl eax, 2 ; inmultim cu 4, avem un DWORD per pixel
	add edi, eax
	push ecx
	mov ecx, symbol_width
	
bucla_simbol_coloane:
	cmp byte ptr [esi], 0
	je simbol_pixel_violet
	mov dword ptr [edi], 0e6e600h ; galben
	jmp simbol_pixel_next
	
simbol_pixel_violet:
	mov dword ptr [edi], 262673h ; violet
	
simbol_pixel_next:
	inc esi
	add edi, 4
	loop bucla_simbol_coloane
	pop ecx
	loop bucla_simbol_linii
	popa
	mov esp, ebp
	pop ebp
	ret
make_text endp
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
random PROC ; functie pentru miscarea fantomelor
    push ebx                 
    push 0
    call time                ; EAX=time(0)
    add esp, 4
    push eax                 ; Use time as seed
    call srand               ; srand(time(0))
    add esp, 4
    call rand                ; Get a random number between 0 and 32767 into EAX
	mov ecx, 4
	div ecx
	mov eax, edx
	
	call rand  
	; Get a random number between 0 and 32767 into EAX
	mov ecx, 4
	div ecx
    pop ebx                  ; Restore callee saved registers
    xor eax, eax             ; Return 0 from our program
    ret
random ENDP
make_block proc
	;functie pt desenat patrate
	push ebp
	mov ebp, esp
	pusha
	
	mov eax, [ebp+arg1] ; citim simbolul de afisat
	lea esi, structures
	
draw_block:
	mov ebx, block_height
	mul ebx
	mov ebx, block_width
	mul ebx
	add esi, eax
	mov ecx, block_height
bucla_block_linii:
	mov edi, [ebp+arg2] ; pointer la matricea de pixeli
	mov eax, [ebp+arg4] ; pointer la coord 
	add eax, block_height
	sub eax, ecx
	mov ebx, area_width
	mul ebx
	add eax, [ebp+arg3] ; pointer la coord x
	shl eax, 2 ; inmultim cu 4, avem un DWORD per pixel
	add edi, eax
	push ecx
	mov ecx, block_width
bucla_block_coloane:
	cmp byte ptr [esi], 0 ;verifica daca e pixel negru
	je block_pixel_negru
	cmp byte ptr [esi], 1
	je block_pixel_mov
	cmp byte ptr [esi], 2
	je block_pixel_galben
	cmp byte ptr [esi], 3
	je block_pixel_rosu
	cmp byte ptr [esi], 4
	je block_pixel_verde
	cmp byte ptr [esi], 5
	je block_pixel_albastru
	
block_pixel_negru: ;catalogul de culori pentru joc
	mov dword ptr [edi], 0
	jmp block_pixel_next
block_pixel_mov:
	mov dword ptr [edi], 262673h
	jmp block_pixel_next
block_pixel_galben:
	mov dword ptr [edi], 0e6e600h
	jmp block_pixel_next
block_pixel_rosu:
	mov dword ptr [edi], 0ff0000h
	jmp block_pixel_next
block_pixel_verde:
	mov dword ptr [edi], 046ff00h
	jmp block_pixel_next
block_pixel_albastru:
	mov dword ptr[edi], 0008BFFh
	jmp block_pixel_next
block_pixel_next:
	inc esi
	add edi, 4
	loop bucla_block_coloane
	pop ecx
	loop bucla_block_linii
	popa
	mov esp, ebp
	pop ebp
	ret
make_block endp

make_block_macro macro symbol, drawArea, x, y
	push y
	push x
	push drawArea
	push symbol
	call make_block
	add esp, 16
endm

state_matrix macro ;afisarea matricii de joc
	local primul_loop
	local al_doilea_loop
	lea esi, game_map ; incarcam matricea
	mov ebx, game_matrix_height ;for(i=0; i<game_matrix_height; i++)
	mov y, 30 ;jos
primul_loop:
	mov ecx, game_matrix_width
	add y, 20
	mov x, -30
al_doilea_loop:
	add x, 30 ;dreapta
	make_block_macro [esi], area, x, y ; afisam matricea
	add esi, 4
	dec ecx
	cmp ecx, 0
	jne al_doilea_loop
	dec ebx
	cmp ebx, 0
	jne primul_loop
endm

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
line_horizontal macro x, y, len, color
local bucla_line
	mov eax, y ; EAX = y
	mov ebx, area_width
	mul ebx ; EAX = y * area_width
	add eax, x ; EAX = y * area_width + x
	shl eax, 2 ; EAX = (y * area_width + x) *4
	add eax, area
	mov ecx, len
bucla_line:
	mov dword ptr[eax], color
	add eax, 4
	loop bucla_line
endm
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
line_vertical macro x, y, len, color
local bucla_line
	mov eax, y ; EAX = y
	mov ebx, area_width
	mul ebx ; EAX = y * area_width
	add eax, x ; EAX = y * area_width + x
	shl eax, 2 ; EAX = (y * area_width + x) *4
	add eax, area
	mov ecx, len
bucla_line:
	mov dword ptr[eax], color
	add eax, area_width * 4
	loop bucla_line
endm
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; un macro ca sa apelam mai usor desenarea simbolului
make_text_macro macro symbol, drawArea, x, y
	push y
	push x
	push drawArea
	push symbol
	call make_text
	add esp, 16
endm
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; functia de desenare - se apeleaza la fiecare click
; sau la fiecare interval de 200ms in care nu s-a dat click
; arg1 - evt (0 - initializare, 1 - click, 2 - s-a scurs intervalul fara click)
; arg2 - x
; arg3 - y
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


calculate_pozition_ghost macro pozition_x, pozition_y
	lea esi, game_map
	mov eax, pozition_y ; EAX = coordonata y al lui pacman
	mov ebx, game_matrix_width
	mul ebx ; EAX = y * lungimea jocului
	add eax, pozition_x ; EAX = y * latimea mapei + x
	shl eax, 2 ; EAX =(y * latimea mapei + x)*DD
	add eax, esi ; adun pointerul game_map
endm

move_right_ghost macro position_x
	local liber
	local continue
	mov ebx, [eax+4]
	mov edx, [eax]
	cmp ebx, 0
	je liber
	cmp ebx, 2
	je liber
	cmp ebx, 3
	je liber
	jne continue
liber:
	xchg ebx, edx
	inc position_x
continue:
	mov [eax+4],ebx ;aici inseram in matrice dupa switch
	mov [eax],edx 
endm

move_left_ghost macro position_x
	local liber
	local continue
	
	mov ebx, [eax-4]
	mov edx, [eax]
	cmp ebx, 0
	je liber
	cmp ebx, 2
	je liber
	cmp ebx, 3
	je liber
	jne continue
liber:
	xchg ebx, edx
	dec position_x
continue:
	mov [eax-4],ebx ;aici inseram in matrice dupa switch
	mov [eax],edx 
endm

move_down_ghost macro position_y
	local liber
	local continue

	mov ebx, [eax+game_matrix_width*4]
	mov edx, [eax]
	cmp ebx, 0
	je liber
	cmp ebx, 2
	je liber
	cmp ebx, 3
	je liber
	jne continue
liber:
	xchg ebx, edx
	inc position_y
	
continue:
	mov [eax+game_matrix_width*4],ebx ;aici inseram in matrice dupa switch
	mov [eax],edx 
endm

move_up_ghost macro position_y
	local liber
	local continue

	mov ebx, [eax-game_matrix_width*4]
	mov edx, [eax]
	cmp ebx, 0
	je liber
	cmp ebx, 2
	je liber
	cmp ebx, 3
	je liber
	jne continue
liber:
	xchg ebx, edx
	dec position_y
	
continue:
	mov [eax-game_matrix_width*4],ebx ;aici inseram in matrice dupa switch
	mov [eax],edx 
endm

calculate_pozition_player macro
	lea esi, game_map
	mov eax, player_y ; EAX = coordonata y al lui pacman
	mov ebx, game_matrix_width
	mul ebx ; EAX = y * lungimea jocului
	add eax, player_x ; EAX = y * latimea mapei + x
	shl eax, 2 ; EAX =(y * latimea mapei + x)*DD
	add eax, esi ; adun pointerul game_map
endm

verificare_gura_deschisa macro ; pentru animatia pacMan-ului
	local deschis
	local skip
	cmp gura_deschisa, 1
	je deschis
	mov gura_deschisa, 1
	jmp skip
deschis:
	add edx, 1
	mov gura_deschisa, 0
skip:
endm
move_right macro
	local liber
	local banut
	local cireasa
	local continue
	local skip
	local skip2
	local gate_right
	local fantoma
	mov ebx, [eax+4]
	mov edx, [eax]
	cmp ebx, 0
	je liber
	cmp ebx, 2
	je banut
	cmp ebx, 3
	je cireasa
	cmp ebx, 4
	je fantoma
	cmp ebx, 5
	je fantoma
	cmp ebx, 6
	je fantoma
	jne continue
liber:
	cmp player_x, 38
	je gate_right
	cmp player_x, 38
	jne skip
gate_right:
	mov ebx, 0 ;pun bloc gol langa gate
	mov [eax], ebx
	mov player_x, 0	; il mut in partea din dreapta
	calculate_pozition_player
	mov ebx, [eax]
	mov ebx, 7 ; incarc fata noua
	jmp skip2
skip:
	verificare_gura_deschisa
	xchg ebx, edx
skip2:
	inc player_x
	jmp continue
banut:
	mov ebx, 0
	verificare_gura_deschisa
	xchg ebx, edx
	inc player_x
	add score, 1
	jmp continue

cireasa:
	mov ebx, 0
	verificare_gura_deschisa
	xchg ebx, edx
	inc player_x
	add score, 10
	jmp continue
	
fantoma:
	mov orientation, 9
continue:
	mov [eax+4],ebx ;aici inseram in matrice dupa switch
	mov [eax],edx 
endm

move_left macro
	local liber
	local banut
	local cireasa
	local continue
	local fantoma
	local gate_left
	local skip;
	local skip2
	mov ebx, [eax-4]
	mov edx, [eax]
	cmp ebx, 0
	je liber
	cmp ebx, 2
	je banut
	cmp ebx, 3
	je cireasa
	cmp ebx, 4
	je fantoma
	cmp ebx, 5
	je fantoma
	cmp ebx, 6
	je fantoma
	jne continue

liber:
	cmp player_x, 1
	je gate_left
	cmp player_x, 1
	jne skip
gate_left:
	mov ebx, 0
	mov [eax], ebx
	mov player_x, 39
	calculate_pozition_player
	mov ebx, [eax]
	mov ebx, 13
	jmp skip2
skip:
	verificare_gura_deschisa
	xchg ebx, edx
skip2:
	dec player_x
	jmp continue
banut:
	mov ebx, 0
	verificare_gura_deschisa
	xchg ebx, edx
	dec player_x
	add score, 1
	jmp continue

cireasa:
	mov ebx, 0
	verificare_gura_deschisa
	xchg ebx, edx
	dec player_x
	add score, 10
	jmp continue
	
fantoma:
	mov orientation, 9
	
continue:
	mov [eax-4],ebx ;aici inseram in matrice dupa switch
	mov [eax],edx 
endm

move_down macro
	local liber
	local banut
	local cireasa
	local continue
	local fantoma
	mov ebx, [eax+game_matrix_width*4]
	mov edx, [eax]
	cmp ebx, 0
	je liber
	cmp ebx, 2
	je banut
	cmp ebx, 3
	je cireasa
	cmp ebx, 4
	je fantoma
	cmp ebx, 5
	je fantoma
	cmp ebx, 6
	je fantoma
	jne continue

liber:
	verificare_gura_deschisa
	xchg ebx, edx
	inc player_y
	jmp continue
banut:
	mov ebx, 0
	verificare_gura_deschisa
	xchg ebx, edx
	inc player_y
	add score, 1
	jmp continue

cireasa:
	mov ebx, 0
	verificare_gura_deschisa
	xchg ebx, edx
	inc player_y
	add score, 10
	jmp continue
	
fantoma:
	mov orientation, 9
	
continue:
	mov [eax+game_matrix_width*4],ebx ;aici inseram in matrice dupa switch
	mov [eax],edx 
endm

move_up macro
	local liber
	local banut
	local cireasa
	local continue
	local fantoma
	mov ebx, [eax-game_matrix_width*4]
	mov edx, [eax]
	cmp ebx, 0
	je liber
	cmp ebx, 2
	je banut
	cmp ebx, 3
	je cireasa
	cmp ebx, 4
	je fantoma
	cmp ebx, 5
	je fantoma
	cmp ebx, 6
	je fantoma
	jne continue

liber:
	verificare_gura_deschisa
	xchg ebx, edx
	dec player_y
	jmp continue
banut:
	mov ebx, 0
	verificare_gura_deschisa
	xchg ebx, edx
	dec player_y
	add score, 1
	jmp continue

cireasa:
	mov ebx, 0
	verificare_gura_deschisa
	xchg ebx, edx
	dec player_y
	add score, 10
	jmp continue
	
fantoma:
	mov orientation, 9
	
continue:
	mov [eax-game_matrix_width*4],ebx ;aici inseram in matrice dupa switch
	mov [eax],edx 
endm
draw proc
	push ebp
	mov ebp, esp
	pusha
	
	mov eax, [ebp+arg1]
	cmp eax, 1
	jz evt_click
	cmp eax, 2
	jz evt_timer ; nu s-a efectuat click pe nimic
	;mai jos e codul care intializeaza fereastra cu pixeli albi
	mov eax, area_width
	mov ebx, area_height
	mul ebx
	shl eax, 2
	push eax
	push 0 ; ====background negru
	push area
	call memset
	add esp, 12
	jmp afisare_litere
	
evt_click: ; aici vad daca s-a dat click undeva
	;verificam daca s-a apasat pe buton
	
	;button w
	mov eax, [ebp+arg2]
	cmp eax, button_w_x
	jl buton_a
	cmp eax, button_w_x + button_size
	jg buton_a
	mov eax, [ebp+arg3]
	cmp eax, button_w_y
	jl buton_a
	cmp eax, button_w_y + button_size
	jg buton_a
	
	make_text_macro 'W', area, 1150, 10
	
	cmp orientation, 9
	je button_fail
	mov desire_orientation, 0
	calculate_pozition_player ; daca este perete unde vrea utilizatorul sa mearga, sa-si pastreze sensul
	mov ebx, [eax-game_matrix_width*4]
	cmp ebx, 1
	je button_fail
	mov orientation, 0
	
	
buton_a:
	;button a
	mov eax, [ebp+arg2]
	cmp eax, button_a_x
	jl buton_s
	cmp eax, button_a_x + button_size
	jg buton_s
	mov eax, [ebp+arg3]
	cmp eax, button_a_y
	jl buton_s
	cmp eax, button_a_y + button_size
	jg buton_s

	make_text_macro 'A', area, 1150, 10
	cmp orientation, 9
	je button_fail
	
	mov desire_orientation, 1
	calculate_pozition_player
	mov ebx, [eax-4]
	cmp ebx, 1
	je button_fail
	mov orientation, 1
	
buton_s:
	;button s
	mov eax, [ebp+arg2]
	cmp eax, button_s_x
	jl buton_d
	cmp eax, button_s_x + button_size
	jg buton_d
	mov eax, [ebp+arg3]
	cmp eax, button_s_y
	jl buton_d
	cmp eax, button_s_y + button_size
	jg buton_d
	
	make_text_macro 'S', area, 1150, 10
	cmp orientation, 9
	je button_fail
	
	mov desire_orientation, 2
	calculate_pozition_player
	mov ebx, [eax+game_matrix_width*4]
	cmp ebx, 1
	je button_fail
	mov orientation, 2

buton_d:
	;button d
	mov eax, [ebp+arg2]
	cmp eax, button_d_x
	jl button_fail
	cmp eax, button_d_x + button_size
	jg button_fail
	mov eax, [ebp+arg3]
	cmp eax, button_d_y
	jl button_fail
	cmp eax, button_d_y + button_size
	jg button_fail

	make_text_macro 'D', area, 1150, 10
	cmp orientation, 9
	je button_fail
	
	mov desire_orientation, 3
	calculate_pozition_player
	mov ebx, [eax+4]
	cmp ebx, 1
	je button_fail
	mov orientation, 3

;3 ->dreapta
;1 ->stanga
;2 ->jos
;0 ->sus	
button_fail:
	jmp afisare_litere
	
evt_timer:
	inc counter	
	cmp score, 400
	jge win
	xor edx, edx
	;miscam fantomele
	call random
	;aici valoare random
	mov orientation_ghost1, edx
	calculate_pozition_ghost ghost1_x, ghost1_y
	lea ebp, structures
	cmp orientation_ghost1, 3
	je dreapta_ghost1
	cmp orientation_ghost1, 1
	je stanga_ghost1
	cmp orientation_ghost1, 2
	je jos_ghost1
	cmp orientation_ghost1, 0
	je sus_ghost1
	
dreapta_ghost1:
	move_right_ghost ghost1_x
	jmp skip_ghost1
	
stanga_ghost1:
	move_left_ghost ghost1_x
	jmp skip_ghost1
	
sus_ghost1:
	move_up_ghost ghost1_y
	jmp skip_ghost1
	
jos_ghost1:
	move_down_ghost ghost1_y
	jmp skip_ghost1
	
skip_ghost1:
	xor edx, edx
	call random
	;aici valoare random
	mov orientation_ghost2, edx
	add edx, 2
	calculate_pozition_ghost ghost2_x, ghost2_y
	lea ebp, structures
	cmp orientation_ghost2, 3
	je dreapta_ghost2
	cmp orientation_ghost2, 1
	je stanga_ghost2
	cmp orientation_ghost2, 2
	je jos_ghost2
	cmp orientation_ghost2, 0
	je sus_ghost2
	
dreapta_ghost2:
	move_right_ghost ghost2_x
	jmp skip_ghost2
	
stanga_ghost2:
	move_left_ghost ghost2_x
	jmp skip_ghost2
	
sus_ghost2:
	move_up_ghost ghost2_y
	jmp skip_ghost2
	
jos_ghost2:
	move_down_ghost ghost2_y
	jmp skip_ghost2
	
skip_ghost2:
	xor edx, edx
	call random
	;aici valoare random
	add edx, 1
	mov orientation_ghost3, edx
	calculate_pozition_ghost ghost3_x, ghost3_y
	lea ebp, structures
	cmp orientation_ghost3, 3
	je dreapta_ghost3
	cmp orientation_ghost3, 1
	je stanga_ghost3
	cmp orientation_ghost3, 2
	je jos_ghost3
	cmp orientation_ghost3, 0
	je sus_ghost3
	
dreapta_ghost3:
	move_right_ghost ghost3_x
	jmp skip_ghost3
	
stanga_ghost3:
	move_left_ghost ghost3_x
	jmp skip_ghost3
	
sus_ghost3:
	move_up_ghost ghost3_y
	jmp skip_ghost3
	
jos_ghost3:
	move_down_ghost ghost3_y
	jmp skip_ghost3
skip_ghost3:
	cmp orientation, 9
	je afisare_litere
	;facem pacman sa se miste
	cmp desire_orientation, 0  
	je verificare_sus
	cmp desire_orientation, 1
	je verificare_stanga
	cmp desire_orientation, 2
	je verificare_jos
;daca a trecut de ultima comparatie, inseamna ca desire_orientation este 3
	calculate_pozition_player
	mov ebx, [eax+4]
	cmp ebx, 1
	je resume
	mov orientation, 3
	jmp resume
	
verificare_stanga:
	calculate_pozition_player
	mov ebx, [eax-4]
	cmp ebx, 1
	je resume
	mov orientation, 1
	jmp resume
	
verificare_jos:
	calculate_pozition_player
	mov ebx, [eax+game_matrix_width*4]
	cmp ebx, 1
	je resume
	mov orientation, 2
	jmp resume
	
verificare_sus:
	calculate_pozition_player
	mov ebx, [eax-game_matrix_width*4]
	cmp ebx, 1
	je resume
	mov orientation, 0

resume:
	cmp orientation, 3
	je dreapta
	cmp orientation, 1
	je stanga
	cmp orientation, 2
	je jos
	cmp orientation, 0
	je sus
	jne afisare_litere
dreapta:
	;punem sa se roteasca capul
	mov ebx, [eax]
	mov ebx, 7
	mov [eax], ebx
	move_right
	jmp afisare_litere
	
stanga:
	mov ebx, [eax]
	mov ebx, 13
	mov [eax], ebx
	move_left
	jmp afisare_litere

jos:
	mov ebx, [eax]
	mov ebx, 9
	mov [eax], ebx
	move_down
	jmp afisare_litere
	
sus:
	mov ebx, [eax]
	mov ebx, 11
	mov [eax], ebx
	move_up
	jmp afisare_litere
	
	
afisare_litere:
	;afisam valoarea counter-ului curent (sute, zeci si unitati)
	mov ebx, 10
	mov eax, counter
	;cifra unitatilor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 30, 10
	;cifra zecilor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 20, 10
	;cifra sutelor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 10, 10
	
	;0e6e6e6h - gri deschis
	;0e6e600h - galben
	;262673h  - violet
	;0ffffffh - alb
	
	make_text_macro '<', area, 530, 10
	make_text_macro ' ', area, 540, 10
	make_text_macro ' ', area, 550, 10
	make_text_macro 'P', area, 560, 10
	make_text_macro 'A', area, 570, 10
	make_text_macro 'C', area, 580, 10
	make_text_macro 'M', area, 590, 10
	make_text_macro 'A', area, 600, 10
	make_text_macro 'N', area, 610, 10
	make_text_macro ' ', area, 620, 10
	make_text_macro ' ', area, 630, 10
	make_text_macro '<', area, 640, 10
	
	make_text_macro 'S', area, 80, 10
	make_text_macro 'C', area, 90, 10
	make_text_macro 'O', area, 100, 10
	make_text_macro 'R', area, 110, 10
	make_text_macro 'E', area, 120, 10
	make_text_macro ' ', area, 130, 10
	
	mov ebx, 10
	mov eax, score
	;cifra unitatilor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 160, 10
	;cifra zecilor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 150, 10
	;cifra sutelor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 140, 10
	
	make_text_macro 'L', area, 1000, 10
	make_text_macro 'I', area, 1010, 10
	make_text_macro 'V', area, 1020, 10
	make_text_macro 'E', area, 1030, 10
	make_text_macro 'S', area, 1040, 10
	make_text_macro ' ', area, 1050, 10
	
	cmp orientation, 9 ; daca a murit, modificam viata
	je switch_live
	make_text_macro '1', area, 1060, 10
	jmp skip
switch_live:
	make_text_macro '0', area, 1060, 10
skip:
	cmp orientation, 10
	je win
	cmp orientation, 9 ;afisam "YOU LOST" daca a murit
	je print_you_lose
	state_matrix
	jmp skip_print
	
print_you_lose:
	state_matrix
	make_text_macro 'Y', area, 585, 390
	make_text_macro 'O', area, 595, 390
	make_text_macro 'U', area, 605, 390
	make_text_macro 'L', area, 580, 420
	make_text_macro 'O', area, 590, 420
	make_text_macro 'S', area, 600, 420
	make_text_macro 'T', area, 610, 420
	jmp skip_print

win:
	state_matrix
	make_text_macro 'Y', area, 585, 390
	make_text_macro 'O', area, 595, 390
	make_text_macro 'U', area, 605, 390
	make_text_macro 'W', area, 585, 420
	make_text_macro 'I', area, 595, 420
	make_text_macro 'N', area, 605, 420
	
skip_print:
	 
	;button w
	line_horizontal button_w_x, button_w_y, button_size, 0e6e600h
	line_horizontal button_w_x, button_w_y+button_size, button_size, 0e6e600h
	line_vertical button_w_x, button_w_y, button_size, 0e6e600h
	line_vertical button_w_x+button_size, button_w_y, button_size, 0e6e600h
	make_text_macro 'W', area, button_w_x+16, button_w_y+10
	
	;button s
	line_horizontal button_s_x, button_s_y, button_size, 0e6e600h
	line_horizontal button_s_x, button_s_y+button_size, button_size, 0e6e600h
	line_vertical button_s_x, button_s_y, button_size, 0e6e600h
	line_vertical button_s_x+button_size, button_s_y, button_size, 0e6e600h
	make_text_macro 'S', area, button_s_x+16, button_s_y+10
	
	;button a
	line_horizontal button_a_x, button_a_y, button_size, 0e6e600h
	line_horizontal button_a_x, button_a_y+button_size, button_size, 0e6e600h
	line_vertical button_a_x, button_a_y, button_size, 0e6e600h
	line_vertical button_a_x+button_size, button_a_y, button_size, 0e6e600h
	make_text_macro 'A', area, button_a_x+16, button_a_y+10
	
	;button d
	line_horizontal button_d_x, button_d_y, button_size, 0e6e600h
	line_horizontal button_d_x, button_d_y+button_size, button_size, 0e6e600h
	line_vertical button_d_x, button_d_y, button_size, 0e6e600h
	line_vertical button_d_x+button_size, button_d_y, button_size, 0e6e600h
	make_text_macro 'D', area, button_d_x+16, button_d_y+10
	
	line_horizontal_big 0, 40, 1200, 262673h, 0e6e600h ;border for status
	line_vertical_big 1010, 48, 200, 262673h, 0e6e600h ;border for status
	line_horizontal_big 1010, 240, 190, 262673h, 0e6e600h ;border for status

	
final_draw:
	popa
	mov esp, ebp
	pop ebp
	ret
draw endp
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
start:
	;alocam memorie pentru zona de desenat
	mov eax, area_width
	mov ebx, area_height
	mul ebx
	shl eax, 2
	push eax
	call malloc
	add esp, 4
	mov area, eax
	;apelam functia de desenare a ferestrei
	push offset draw
	push area
	push area_height
	push area_width
	push offset window_title
	call BeginDrawing
	add esp, 20
	
	;terminarea programului
finish:
	
	jmp finish
	push 0
	call exit
end start
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;