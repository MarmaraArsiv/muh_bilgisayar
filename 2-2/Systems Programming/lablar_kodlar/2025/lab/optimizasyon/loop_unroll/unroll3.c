int unroll(int *array){
	int i;
	int dummy=1;
	for(i=1;i<10;i++){
		dummy*=array[i];
	}
	return dummy;
}

