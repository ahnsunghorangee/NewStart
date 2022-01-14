import { createRangeIterable } from "./iterators/createRangeIterable";

const iteraotr = createRangeIterable(1, 3+1) // 반복기 동작 x

while(true){
    const {value, done} = iteraotr.next() // 반복기 동작 o
    if(done)
        break
    console.log(value)
}