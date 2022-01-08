import React, {Component} from 'react';

class Counter extends Component{

    // state 초기화 방법1. 생성자
    constructor(props){ // 생성자
        super(props); // 필수
        this.state = { // 초기값 설정
            number: 0,
            fixedNumber:0
        }
    }

    render(){
        const {number, fixedNumber} = this.state; // state 조회

        return(
            <div>
                <h1>{number}</h1>
                <h2>바뀌지 않는 값: {fixedNumber}</h2>
                <button onClick={() => {
                    this.setState(prevState => ({
                        number: prevState.number+1
                    }),
                    () => {
                        console.log("콜백함수 호출");
                        console.log(this.state);
                    });
                }}>+1</button>
            </div>
        );
    }
}

export default Counter;