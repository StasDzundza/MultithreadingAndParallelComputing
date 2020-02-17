package main

import (
	"fmt"
	"math/rand"
	"sync"
)

type CyclicBarrier struct {
	generation int
	count      int
	parties    int
	trip       *sync.Cond
}

func (b *CyclicBarrier) nextGeneration() {
	// signal completion of last generation
	b.trip.Broadcast()
	b.count = b.parties
	// set up next generation
	b.generation++
}

func (b *CyclicBarrier) Await() {
	b.trip.L.Lock()
	defer b.trip.L.Unlock()

	generation := b.generation

	b.count--
	index := b.count
	//println(index)

	if index == 0 {
		b.nextGeneration()
	} else {
		for generation == b.generation {
			//wait for current generation complete
			b.trip.Wait()
		}
	}
}

func NewCyclicBarrier(num int) *CyclicBarrier {
	b := CyclicBarrier{}
	b.count = num
	b.parties = num
	b.trip = sync.NewCond(&sync.Mutex{})

	return &b
}

var numbers1 = [5]int{1,2,3,4,5}
var numbers2 = [5]int{2,2,3,4,5}
var numbers3 = [5]int{1,5,3,4,5}
var r = rand.New(rand.NewSource(55))

func main() {
	b := NewCyclicBarrier(3)
	go array_work(1,b)
	go array_work(2,b)
	go array_work(3,b)

	fmt.Scanln()
}

func array_work(arr_index int, b *CyclicBarrier) {
	for{
		operation:=r.Intn(2);
		index1 := r.Intn(5);
		if arr_index==1{
			if operation==0{
				numbers1[index1] = numbers1[index1]-1
			}else{
				numbers1[index1] = numbers1[index1]+1
			}
		}else if arr_index==2{
			if operation==0{
				numbers2[index1] = numbers2[index1]-1
			}else{
				numbers2[index1] = numbers2[index1]+1
			}
		}else{
			if operation==0{
				numbers3[index1] = numbers3[index1]-1
			}else{
				numbers3[index1] = numbers3[index1]+1
			}
		}

		b.Await()
		var sum1,sum2,sum3 int = 0,0,0
		for i:=0; i<5;i++{
			sum1+=numbers1[i]
			sum2+=numbers2[i]
			sum3+=numbers3[i]
		}
		//b.Await()
		if(sum1==sum2 && sum2 == sum3){
			fmt.Printf("Sums %d are equal\n",sum1)
			break
		}else{
			fmt.Printf("Sums %d,%d,%d aren`t equal\n",sum1,sum2,sum3)
		}
	}
}