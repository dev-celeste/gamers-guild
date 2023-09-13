import { useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router";
import AuthContext from "../context/AuthContext";


const BLANK_GAME= {
    gameTitle: ""
}

function GameSearchBar() {
    const [games, setGames] = useState([]);
    const [foundGames, setFoundGames] = useState([]);
    const [game, setGame] = useState(BLANK_GAME);
    

    const [errors, setErrors] = useState([]);
    const [messages, setMessages] = useState("");
    const auth = useContext(AuthContext);

    const BLANK_GAMER_GAME = {
        gamerId: auth.userGamer.gamerId,
        game: {}
    };
    const [gamerGame, setGamerGame] = useState(BLANK_GAMER_GAME);
    const navigate = useNavigate();
    const url = "http://localhost:8080/game";

    useEffect( () => {
        fetch(`${url}/game`)
        .then(response => {
            if (response.status === 200) {
                return response.json();
            } else {
                return Promise.reject(`Unexpected status code: ${response.status}`);
            }
        })
        .then(data => {
            setGames(data);
            console.log(data)
        }) 
        .catch(console.log);
    }, [games.length]);

    const handleAddNewGame= (event) => {
        event.preventDefault();
        const init = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(game)
        };
        fetch(url, init)
        .then(response => {
            if(response.status === 201 || response.status === 400){
                return response.json();
            }else{
                return Promise.reject(`Unexpected status code: ${response.status}`);
            }
        })
        .then(data =>{
            if(data.gameId){
                     
                setMessages(`The game ${game.gameTitle} was successfully added to the list!`);
            } else{
                setErrors(data);
            }
        })
        .catch(console.log)
    }

    const handleChangeGame = (event) => {
        const nextGame = {...game};
        nextGame[event.target.name] = event.target.value;
        setGame(nextGame);
        searchGamesByTitle(event.target.value);
        // for bugtesting
        console.log(nextGame);
    }

    const searchGamesByTitle = (value) => {
        fetch(url)
        .then(response => {
            if (response.status === 200) {
                return response.json();
            } else {
                return Promise.reject(`Unexpected status code: ${response.status}`);
            }
        })
        .then(data => {
            const newFoundGames = data.filter((game) => {
                return value && game.gameTitle.toLowerCase().includes(value.toLowerCase());
            });
            setFoundGames(newFoundGames);
        }) 
        .catch(console.log);
    } 

    const handleAddGamerGame = (gameId) => {
        console.log(gameId);
        const newGamerGame = {...gamerGame};
        const gameToAdd = games.find(game => game.gameId === gameId);
        newGamerGame.game = gameToAdd;

        console.log(newGamerGame);
        const init = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(newGamerGame)
        };
        fetch(`${url}r/game`, init)
        .then(response => {
            if(response.status === 201) {
                return null;
            } else if( response.status === 400){
                return response.json();
            }else{
                return Promise.reject(`Unexpected status code: ${response.status}`);
            }
        })
        .then(data =>{
            console.log(data);
            if(!data){

                setMessages(`You added ${gameToAdd.gameTitle} as a favorite game!`);
            } else{
                setErrors(data);
            }
        })
        .catch(console.log)
    }

    return (
        <div>
            <p>Search for a game?</p>
            <input id="gameTitle" 
            name="gameTitle" 
            type="text" 
            className="form-control" 
            placeholder="Type in game title..."
            onChange={handleChangeGame}/>
            
            <button className="btn btn-success mr-2" onClick={handleAddNewGame}>
                Add game!
            </button>
            <p>{messages}</p>
            {errors.length > 0 && (
                <div className="alert alert-danger">
                    <p>The following errors were found:</p>
                    <ul>
                        {errors.map(error => 
                        <li key={error}>{error}</li>
                        )}
                    </ul>
                </div>
            )}
            <p>Don't see your game in the results? Add a game to the list!</p>
            <p>Search results:</p>
            <table>
            <thead>
                <tr>
                    <th>Game Title</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                {(foundGames.length > 0) ? (
                    foundGames.map(foundGame => (
                        <tr key = {foundGame.gameId}>
                            <td><strong>{foundGame.gameTitle}</strong></td>
                            <td><button onClick={() => handleAddGamerGame(foundGame.gameId)} type="button">Add Game</button></td>
                        </tr>
                        ))
                ) : (
                    games.map(game => (
                    <tr key = {game.gameId}>
                        <td>{game.gameTitle} </td>
                        <td><button onClick={() => handleAddGamerGame(game.gameId)} type="button">Add Game</button></td>
                    </tr>
                    ))
                )}

            </tbody>
        </table>
        
            {/* <div>
                {foundGames.map(foundGame => (
                    <div key = {foundGame.gameId}>
                        <p><strong>{foundGame.gameTitle}</strong></p>
                    </div>
                ))}
            </div> */}

        </div>
    );
    
}

export default GameSearchBar;
