import { useEffect, useState } from "react";

function FindGameTitle(props) {
    const [game, setGame] = useState({});
    const game_url = 'http://localhost:8080/game'

    useEffect(() => {
        fetch(`${game_url}/id/${props.currentGameId}`)
        .then(response => {
            if (response.status === 200) {
                return response.json();
            } else {
                return Promise.reject(`Unexpected status code: ${response.status}`);
            }
        })
        .then(data => setGame(data)) // here we are setting our data to our state variable 
        .catch(console.log);
    
    }, []); // empty dependency array tells react to run once when the component is intially loaded
    return(
        <>
        <p className="postGame">{game.gameTitle}</p>
        </>
    );
}

export default FindGameTitle;