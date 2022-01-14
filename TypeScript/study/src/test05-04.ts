import { pureSort } from "./purefunctions/pureSort";
import {pureDelete} from "./purefunctions/pureDelete"


let beforeSort = [6,2,9,0]
const afterSort = pureSort(beforeSort)
console.log(beforeSort, afterSort)


const mixedArray:object[] = [
    [], {name:'Jack'},{name:'Jane', age: 32}, ['description']
]
const objectOnly: object[] = pureDelete(mixedArray, (val) => Array.isArray(val))
console.log(mixedArray, objectOnly)