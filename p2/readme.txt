{\rtf1\ansi\ansicpg1252\cocoartf1561\cocoasubrtf400
{\fonttbl\f0\fswiss\fcharset0 Helvetica;\f1\fnil\fcharset0 Menlo-Regular;}
{\colortbl;\red255\green255\blue255;\red0\green0\blue0;\red255\green255\blue255;}
{\*\expandedcolortbl;;\csgray\c0;\csgray\c100000;}
\margl1440\margr1440\vieww10800\viewh8400\viewkind0
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\f0\fs24 \cf0 Compile source file:\
               javac sys1.java\
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0
\cf0                javac sys2.java\
\
Run the program:\
              Non-verbose mode:\
		              java sys1 tracefile cachesize \
			   java sys1 tracefile cachesize  ways\
	  verbose mode:\
		              java sys1 tracefile cachesize -v ic1 ic2\
			   java sys1 tracefile cachesize  ways -v ic1 ic2\
\
\
Example:\
\pard\tx560\tx1120\tx1680\tx2240\tx2800\tx3360\tx3920\tx4480\tx5040\tx5600\tx6160\tx6720\pardirnatural\partightenfactor0

\f1\fs22 \cf2 \cb3 \CocoaLigature0 [swang6@unixlab ~]$ cd P2\
[swang6@unixlab P2]$ javac sys1.java \
[swang6@unixlab P2]$ java sys1 ~whsu/csc656/Traces/S18/P1/gcc.xac 2\
direct-mapped, writeback, size = 2KB\
loads 215150 stores 105331 total 320481\
rmiss 31973 wmiss 11230 total 43203\
dirty rmiss 13595 dirty wmiss 4166\
bytes read 691248 bytes write 284176\
read time 3860590 write time 1337011\
miss rate 0.134807\
total access time 5197601\
[swang6@unixlab P2]$ \
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\f0\fs24 \cf0 \cb1 \CocoaLigature1 \
\pard\tx560\tx1120\tx1680\tx2240\tx2800\tx3360\tx3920\tx4480\tx5040\tx5600\tx6160\tx6720\pardirnatural\partightenfactor0

\f1\fs22 \cf2 \cb3 \CocoaLigature0 [swang6@unixlab P2]$ javac sys2.java \
[swang6@unixlab P2]$ java sys2 ~whsu/csc656/Traces/S18/P1/gcc.xac 2 2\
2-way, writeback, size = 2KB\
loads 215150 stores 105331 total 320481\
rmiss 24534 wmiss 8795 total 33329\
dirty rmiss 10290 dirty wmiss 3018\
bytes read 533264 bytes write 212928\
read time 3001070 write time 1050371\
miss rate 0.103997\
total access time 4051441\
[swang6@unixlab P2]$
\f0\fs24 \cf0 \cb1 \CocoaLigature1 \
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0
\cf0 \
			   }