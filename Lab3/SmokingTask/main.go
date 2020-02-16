package main
import (
	"bytes"
	"fmt"
	 "math/rand"
	"runtime"
	"strconv"
	"time"
)

var r = rand.New(rand.NewSource(55))

func getGID() uint64 {
	b := make([]byte, 64)
	b = b[:runtime.Stack(b, false)]
	b = bytes.TrimPrefix(b, []byte("goroutine "))
	b = b[:bytes.IndexByte(b, ' ')]
	n, _ := strconv.ParseUint(string(b), 10, 64)
	return n
}

func smokerFunc(ingradients, prod_synchronizator, smoker_synchronizator chan int){

	var ingr1 int = r.Intn(3) + 1;
	fmt.Println(getGID()," has a ", ingr1)
	for {
		<-smoker_synchronizator
		var ingr2 int = <-ingradients
		var ingr3 int = <-ingradients
		if (ingr1+ingr2+ingr3) == 6{
			fmt.Println(getGID(), " start smoking")
			time.Sleep(100 * time.Millisecond)
			fmt.Println(getGID()," finish smoking ")
		}
		prod_synchronizator<-1
	}

}


func producerFunc(ingradients, prod_synchronizator, smoker_synchronizator chan int) {
	var ingr1 int = r.Intn(3) + 1;
	var ingr2 int = r.Intn(3) + 1;
	for {
		<-prod_synchronizator
		ingr1 = r.Intn(3) + 1;
		ingr2 = r.Intn(3) + 1;
		fmt.Println("Producer gives a ",ingr1," and ",ingr2)
		ingradients<-ingr1
		ingradients<-ingr2
		smoker_synchronizator<-1
	}
}
func main(){
	ingradients:=make(chan int,2)
	prod_synchronizator:=make(chan int)
	smoker_synchronizator:=make(chan int)

	go smokerFunc(ingradients,prod_synchronizator,smoker_synchronizator)
	go smokerFunc(ingradients,prod_synchronizator,smoker_synchronizator)
	go smokerFunc(ingradients,prod_synchronizator,smoker_synchronizator)
	go producerFunc(ingradients,prod_synchronizator,smoker_synchronizator)
	prod_synchronizator<-1
	fmt.Scanln();
}
