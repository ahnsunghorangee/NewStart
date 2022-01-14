import { readFilePromise } from "./promise/readFilePromise";

readFilePromise('../package.json')
    .then((content: string) => { 
        console.log(content) // package.json 파일을 읽은 내용
        return readFilePromise('../tsconfig.json')
    })
    .then((content: string)=>{ 
        console.log(content) // tsconfig.json 파일을 읽은 내용
        return readFilePromise('.')
    })
    .catch((err:Error) => console.log('error: ',err.message))
    .finally(() => console.log('프로그램 종료'))