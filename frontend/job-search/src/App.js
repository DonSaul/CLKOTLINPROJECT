import logo from './logo.svg';
import './App.css';
import RouterWrapper from './router/router';
import ButtonAppBar from './components/Toolbar';
import { ToastContainer } from 'react-toastify';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function App() {
  return (
    <div className="App">



      <RouterWrapper />

      <ToastContainer position="top-right" autoClose={3000} />

    </div>
  );
}

export default App;
