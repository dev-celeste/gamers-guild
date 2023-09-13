import { useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router";
import AuthContext from "../context/AuthContext";
import { Link } from "react-router-dom";

const BLANK_GAME= {
    gameTitle: ""
}

function GameList(props) {
    const auth = useContext(AuthContext);

    const [games, setGames] = useState([]);
    const [foundGames, setFoundGames] = useState([]);
    const [game, setGame] = useState(BLANK_GAME);
    const BLANK_GAMER_GAME = {
        gamerId: auth.userGamer.gamerId,
        game: {}
    };
    const [gamerGame, setGamerGame] = useState(BLANK_GAMER_GAME);

    const [messages, setMessages] = useState("");
    const [errors, setErrors] = useState([]);
    
    const navigate = useNavigate();
    const url = "http://localhost:8080/game";

    useEffect( () => {
        fetch(url)
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
    }, []);

    const handleDelete = (gameId) => {
        const gameToDelete = games.find(game => game.gameId === gameId);
        if(window.confirm(`Delete ${gameToDelete.gameTitle} from the game list?`)) {
            const init = {
                method: "DELETE"
            };
            fetch(`${url}/${gameId}`, init)
            .then(response => {
                if (response.status === 204) {
                    const newGames = games.filter(game => game.gameId !== gameId);
                    setGames(newGames);
                    navigate("/success", {state: {message: `The game ${gameToDelete.gameTitle} was deleted from the list.`}});
                } else {
                    return Promise.reject(`${response.status}: It's possible this game is being used somewhere! Delete failed.`);
                }
            })
            .catch(data => setErrors([data]));
        }
    }

    const handleAdd= (event) => {
        event.preventDefault();
        const init = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(game)
        };
        fetch(`${url}`, init)
        .then(response => {
            if(response.status === 201 || response.status === 400){
                return response.json();
            }else{
                return Promise.reject(`Unexpected status code: ${response.status}`);
            }
        })
        .then(data =>{
            if(data.gameId){
                navigate("/success", {state: {message: `The game ${game.gameTitle} was successfully added to the list of games!`}})
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
        fetch(`${url}`)
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
                navigate("/success", {state: {message: `You added ${gameToAdd.gameTitle} as a favorite game!`}});
            } else{
                setErrors(data);
            }
        })
        .catch(console.log)
    }

    return (
        <div className="gameListSearchDiv">
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
            <p>{messages}</p>
            
            <p>Search for a game?</p>
            <input id="gameTitle" 
            name="gameTitle" 
            type="text" 
            className="form-control" 
            placeholder="Type in game title..."
            onChange={handleChangeGame}/>
            
            {props.isComponent ? (
                <div>
                    <p>Don't see your game in the results? Go to Games List and add it!</p>
                    <Link to="/game">Games List</Link>
                </div>
            ) : (
                <div>
                    <button className="btn btn-success mr-2" onClick={handleAdd}>
                    Add game!
                    </button>
                <p>Don't see your game in the results? Add a game to the list!</p>
               </div>

            )}


            <p>Search results:</p>
            <table>
            <thead>
                <tr>
                    <th>Game Title</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                {(foundGames.length <= 0 ) ? (
                    (game.gameTitle ? (
                        <tr>
                            <td>No games found!</td>
                            <td></td>
                        </tr>
                    ) : (
                        games.map(game => (
                            <tr key = {game.gameId}>
                                <td>{game.gameTitle} </td>
                                    {props.isComponent ? (
                                    <td><button onClick={() => handleAddGamerGame(game.gameId)} type="button">Add Fav Game</button></td>
                                    ) : (
                                    <td><button onClick={() => handleDelete(game.gameId)} type="button">Remove Game</button></td>
                                    )}
                            </tr>
                        ))
                    ))
                ) : (
                    foundGames.map(foundGame => (
                        <tr key = {foundGame.gameId}>
                            <td><strong>{foundGame.gameTitle}</strong></td>
                            {props.isComponent ? (
                            <td><button onClick={() => handleAddGamerGame(foundGame.gameId)} type="button">Add Fav Game</button></td>
                            ) : (
                            <td><button onClick={() => handleDelete(foundGame.gameId)} type="button">Remove Game</button></td>
                            )}
                        </tr>
                    )))}

            </tbody>
        </table>
        
    </div>
    )
}

export default GameList;
