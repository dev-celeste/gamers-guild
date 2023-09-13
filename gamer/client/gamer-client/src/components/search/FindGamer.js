import { useEffect, useState } from "react";


function FindGamer(props) {
    const [gamer, setGamer] = useState({});
    const gamer_url = 'http://localhost:8080/gamer'

    useEffect(() => {
        fetch(`${gamer_url}/${props.currentGamerId}`)
        .then(response => {
            if (response.status === 200) {
                return response.json();
            } else {
                return Promise.reject(`Unexpected status code: ${response.status}`);
            }
        })
        .then(data => setGamer(data)) // here we are setting our data to our state variable 
        .catch(console.log);
    
    }, []); // empty dependency array tells react to run once when the component is intially loaded
    return(
        <>
        <p className="postGamer">{gamer.gamerTag}</p>
        </>
    );
}

export default FindGamer;