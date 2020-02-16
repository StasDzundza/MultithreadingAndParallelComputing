package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

func chec_path(graph[][]int,rw_mutex *sync.RWMutex){
	for{
		a:=rand.Intn(10);
		b:=rand.Intn(10);
		rw_mutex.RLock()
		if graph[a][b] != 0{
			fmt.Printf("There are is path between %d and %d for price %d\n",a,b,graph[a][b]);
		}else{
			fmt.Printf("There no is path between %d and %d\n",a,b)
		}
		rw_mutex.RUnlock()
	}
}

func remove_path(graph[][]int,rw_mutex *sync.RWMutex){
	for{
		a:=rand.Intn(10);
		b:=rand.Intn(10);
		rw_mutex.Lock()
		if graph[a][b] !=0 {
			graph[a][b] = 0
			fmt.Printf("Path between %d and %d was removed\n", a, b)
		}
		rw_mutex.Unlock()
	}
}

func add_path(graph[][]int,rw_mutex *sync.RWMutex){
	for{
		a:=rand.Intn(10);
		b:=rand.Intn(10);
		price:=rand.Intn(10)+1
		rw_mutex.Lock()
		if graph[a][b] == 0{
			graph[a][b] = price
			fmt.Printf("Path between %d and %d was added with price %d\n",a,b,price)
		}
		rw_mutex.Unlock()
	}
}

func change_price(graph[][]int,rw_mutex *sync.RWMutex){
	for{
		a:=rand.Intn(10);
		b:=rand.Intn(10);
		price:=rand.Intn(10)+1
		rw_mutex.Lock()
		if graph[a][b] != 0{
			graph[a][b] = price
			fmt.Printf("Price between %d and %d was changed to %d\n",a,b,price)
		}
		rw_mutex.Unlock()
	}
}

func main(){
	graph := make([][]int, 10)
	rand.Seed(time.Now().UTC().UnixNano())
	for i:=0; i < 10; i++{
		graph[i] = make([]int,10);
		for j:= 0; j < 10; j++{
			graph[i][j] = rand.Intn(10);
		}
	}

	var rw_mutex sync.RWMutex
	go add_path(graph,&rw_mutex)
	go remove_path(graph,&rw_mutex)
	go change_price(graph,&rw_mutex)
	go chec_path(graph,&rw_mutex)

	fmt.Scanln();
}
