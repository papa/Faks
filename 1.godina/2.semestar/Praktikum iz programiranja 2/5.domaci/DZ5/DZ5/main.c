#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>
#include "functions.h"

int main(int argc, const char* argv[])
{
	if (argc < 7)
	{
		printf("ARG_GRESKA");
		return 0;
	}
	FILE* inA;
	FILE* inB;
	FILE* out_file;
	FOPEN(out_file,argv[1],"w")
	for (int k = 6;k < argc;k++)
	{
		char file_name1[256];
		char file_name2[256];
		char* dira = "_dirA.txt";
		char* dirb = "_dirB.txt";
		int i = 0;
		while (argv[k][i] != '\0')
		{
			file_name1[i] = argv[k][i];
			file_name2[i] = argv[k][i];
			i++;
		}
		file_name1[i] = file_name2[i] = '\0';
		strcat(file_name1, dira);
		strcat(file_name2, dirb);
		FOPEN(inA, file_name1, "r")
		FOPEN(inB, file_name2,"r")
		node* headA = read_bus_stops(inA);
		node* headB = read_bus_stops(inB);
		point start;
		point end;
		sscanf(argv[2], "%lf", &start.lat);
		sscanf(argv[3], "%lf", &start.lon);
		sscanf(argv[4], "%lf", &end.lat);
		sscanf(argv[5], "%lf", &end.lon);

		char ch = 'A';
		node* sol = form_list(headA, headB, start, end, &ch);

		print_file(sol, ch, argv[k], out_file);

		free_memory(headA, headB);
		free_list(sol);

		fclose(inA);
		fclose(inB);
	}
	fclose(out_file);
	return 0;
}