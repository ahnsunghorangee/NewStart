import MyComponent from "./components/MyComponent";
import Counter from "./components/Counter"
import Say from "./components/Say"
import EventPractice from './components/EventPractice'
import ValidationSample from './components/ValidationSample'

function App() {
  return (
    <>
    <div>
      <h1>3장 컴포넌트</h1>
        <MyComponent>childern</MyComponent>
        <Counter></Counter>
        <Say></Say>
      </div>
      <div>
        <h1>4장 이벤트 핸들링</h1>
        <EventPractice></EventPractice>
        <ValidationSample></ValidationSample>
      </div>
    </>
  );
}

export default App;
