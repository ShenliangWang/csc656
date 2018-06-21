/****
File: findRedsDriver.cu
Date: 5/14/2018
By: Shenliang Wang
Compile: nvcc findRedsDriver.cu -o findreadsdriver
Run: ./findreadsdriver

****/

#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <cuda.h>

#define NUMPARTICLES 32768
#define NEIGHBORHOOD .05
#define THREADSPERBLOCK 128

void initPos(float *);
float findDistance(float *, int, int);
__device__ float findDistanceGPU(float *, int, int);
void dumpResults(int index[]);

__global__ void findRedsGPU(float *p, int *numI);

int main() {
    cudaEvent_t start, stop;
    float time;

    float *pos, *dpos;
    int *numReds, *dnumReds;

    pos = (float *) malloc(NUMPARTICLES * 4 * sizeof(float));
    numReds = (int *) malloc(NUMPARTICLES * sizeof(int));

    initPos(pos);

// your code to allocate device arrays for pos and numReds go here

    cudaMalloc((void **)&dpos,NUMPARTICLES * 4 * sizeof(float));

    cudaMalloc((void **)&dnumReds,NUMPARTICLES * sizeof(int));

    cudaMemcpy(dpos,pos,NUMPARTICLES * 4 * sizeof(float),cudaMemcpyHostToDevice);


// create timer events
    cudaEventCreate(&start);
    cudaEventCreate(&stop);

    cudaEventRecord(start, 0);

/* invoke kernel findRedsGPU here */

    findRedsGPU<<<NUMPARTICLES/THREADSPERBLOCK,THREADSPERBLOCK>>>(dpos,dnumReds);

    cudaThreadSynchronize();

// your code to copy results to numReds[] go here

    cudaMemcpy(numReds,dnumReds,NUMPARTICLES * sizeof(int),cudaMemcpyDeviceToHost);

    cudaEventRecord(stop, 0);
    cudaEventSynchronize(stop);
    cudaEventElapsedTime(&time, start, stop);

    printf("Elapsed time = %f\n", time);

    dumpResults(numReds);

}

void initPos(float *p) {

// your code for initializing pos goes here
    int i;
    int j;
    for (i=0; i<NUMPARTICLES; i++) {
    p[i*4] = rand() / (float) RAND_MAX;
    p[i*4+1] = rand() / (float) RAND_MAX;
    p[i*4+2] = rand() / (float) RAND_MAX;
    j = rand() % 3;
    if (j == 0)
        p[i*4+3] = 0xff0000;
    else if (j == 1)
        p[i*4+3] = 0x00ff00;
    else
    p[i*4+3] = 0x0000ff;
}


}

__device__ float findDistanceGPU(float *p, int i, int j) {

// your code for calculating distance for particle i and j

    float x, y, z;

    x = p[i*4] - p[j*4];
    y = p[i*4+1] - p[j*4+1];
    z = p[i*4+2] - p[j*4+2];

    return(sqrt(x*x + y*y + z*z));

}

__global__ void findRedsGPU(float *p, int *numI) {

    int index = blockDim.x * blockIdx.x + threadIdx.x;
    int i;
    float d;

    numI[index] = 0;
    for (i=0; i<NUMPARTICLES; i++) {
        if (index!=i) {
            d = findDistanceGPU(p, index, i);
            if (d < NEIGHBORHOOD && p[i*4+3] == 0xff0000) {
                numI[index]++;
            }
        }
    }

}
void dumpResults(int index[]) {
    int i;
    FILE *fp;

    fp = fopen("./dump.out", "w");

    for (i=0; i<NUMPARTICLES; i++) {
        fprintf(fp, "%d %d\n", i, index[i]);
    }
    fclose(fp);
}
