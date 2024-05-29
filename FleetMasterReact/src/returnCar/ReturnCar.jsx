import { API_BASE_URL } from "../config";
import "./ReturnCar.css"

export const ReturnCar = ({carId}) => {
  const returnCar = async()=>{
    const userToken = "Bearer " + sessionStorage.getItem("Token");
    const userId = sessionStorage.getItem("userId");
      try{
      const response = await fetch(`${API_BASE_URL}/cars/${carId}/users/${userId}/return`,{
        method: "DELETE",
        headers:{
          "Content-Type": "application/json",
          Authorization: userToken,
        }
      })
      if (!response.ok) {
        const message = await response.json();
        alert(`Błąd: ${message.message}`);
        return;
      }

      alert('Samochód został zwrócony pomyślnie!');
      window.location.reload();
    } catch (error) {
      alert('Wystąpił błąd podczas zwracania samochodu. Spróbuj ponownie później.');
      console.error('Error:', error);
    }
    };
    
  return (
    <button onClick={returnCar} className='return-btn'> Return </button>
  )
}
