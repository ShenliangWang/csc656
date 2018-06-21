{\rtf1\ansi\ansicpg1252\cocoartf1561\cocoasubrtf400
{\fonttbl\f0\froman\fcharset0 Times-Roman;}
{\colortbl;\red255\green255\blue255;\red0\green116\blue0;\red255\green255\blue255;\red0\green0\blue0;
}
{\*\expandedcolortbl;;\csgenericrgb\c0\c45600\c0;\csgray\c100000;\cssrgb\c0\c0\c0;
}
\margl1440\margr1440\vieww28300\viewh15380\viewkind0
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\f0\fs24 \cf0 Part a: MaxCol.cu\
1. ~whsu/lees.bash_profile\cf2 \cb3 \
\cf0 \cb1 2. nvcc MaxCol.cu -o maxcol\
3. ./maxcol [matrixSize] [threads]\
\
\
\
Part b: \
For CPU part: \cf4 \expnd0\expndtw0\kerning0
findRedsDriver.cu\cf0 \kerning1\expnd0\expndtw0 \
1.gcc findReds.c -O3 -o findReds -lm\
2. ./findReds\
\
Only need to change data is # of the NUMPARTICLES ( 1024 , 8192,  32768)\
\
For GPU part:\
\
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0
\cf0 1. ~whsu/lees.bash_profile\
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0
\cf0 2. \cf4 \expnd0\expndtw0\kerning0
nvcc findRedsDriver.cu -o findreadsdriver \
3. ./findreadsdriver \
\
Data will change in \cf0 \kerning1\expnd0\expndtw0  # of the NUMPARTICLES ( 1024 , 8192,  32768) and # of the \cf4 \expnd0\expndtw0\kerning0
THREADSPERBLOCK( 4, 16 ,64)\
\
Every-time change the \cf0 \kerning1\expnd0\expndtw0 NUMPARTICLES  or \cf4 \expnd0\expndtw0\kerning0
THREADSPERBLOCK, need replace the findRedsDriver.cu file and redo the step 1-3.\cf4 \
\
}