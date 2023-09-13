import { useContext, useEffect, useState } from "react";
import AuthContext from "../context/AuthContext";
import { Link, useNavigate, useParams } from "react-router-dom";
import FindPostsByGamer from "./FindPostsByGamer";

function GamerProfile() {
    const auth = useContext(AuthContext);
    
    const BLANK_GAMER_PROFILE = {
        gamerId:"999",
        genderType:"PREFER_NOT_TO_SAY",
        gamerTag:"GamerTag",
        birthDate:"2000-01-01",
        bio:"InsertBioHere",
        games: [
            {gamerId:"999",
            game: {
                gameId:"1",
                gameTitle:"Game1"
            }}, 
            {gamerId:"999",
            game: {
                    gameId:"2",
                    gameTitle:"Game2"
            }}
        ],
        sentMatches: [
            {gamerSenderId: "999",
            gamerReceiver: {
                gamerId:"1",
                gamerTag:"sent_to_other1"
            }},
            {gamerSenderId: "999",
                gamerReceiver: {
                gamerId:"2",
                gamerTag:"sent_to_other2"
            }}
        ],
        receivedMatches: [
            {gamerReceiverId: "999",
            gamerSender: {
                gamerId:"1",
                gamerTag:"i_got_this1"
            }},
            {gamerReceiverId: "999",
            gamerSender: {
                gamerId:"2",
                gamerTag:"i_got_this2"
            }}
        ]
    };
    const [gamer, setGamer] = useState(BLANK_GAMER_PROFILE);
    
    const today = new Date();
    const adjustedTodayForTimezome = new Date(today.getTime() - today.getTimezoneOffset() * 60000);

    console.log(adjustedTodayForTimezome);
    const BLANK_MATCH_TO_SEND = {
        dateMatchSent: adjustedTodayForTimezome.toISOString().split("T")[0],
        gamerSenderId: auth.userGamer.gamerId,
        gamerReceiver: gamer
    };
    const [match, setMatch] = useState(BLANK_MATCH_TO_SEND);
    const [errors, setErrors] = useState([]);

    console.log(match.dateMatchSent);

    let { id } = useParams();
    const navigate = useNavigate();
    const url = "http://localhost:8080/gamer";

    // get user profile information
    useEffect( () => {
        console.log(`You refreshed the page probably. here's the id: ${id}`);
        if (!id) {
            console.log(`this should be null if url is /profile : ${id}`);
            console.log(auth.userGamer);
            console.log(`setting new id as ${auth.userGamer.gamerId}`);    
            id = auth.userGamer.gamerId;
        }
        if (id) {
            fetch(`${url}/${id}`)
            .then(response => {
                if (response.status === 200) {
                    return response.json();
                } else {
                    return Promise.reject(`Unexpected status code: ${response.status}`);
                }
            })
            .then( data => {
                setGamer(data);
                console.log(data);
            })
            .catch(console.log);
        }
    }, []); 

    const handleAddMatch = () => {
        console.log("this gamer will be added to the match:");
        console.log(gamer);
        const newMatch = {...match};
        newMatch.gamerReceiver = gamer;
        console.log("this is the match:");
        console.log(newMatch);
    
        const init = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newMatch)
        }
        fetch(`${url}/match`, init)
        .then(response => {
            if (response.status === 201) {
                console.log("Success! Match was added!");
                return null;
            } else if (response.status === 400) {
                console.log("Oops, something went wrong...");
                return response.json();
            } else {
                return Promise.reject(`Unexpected status code: ${response.status}`);
            }
        })
        .then (data => {
            if (!data) {
                console.log("Now redirecting...");
                navigate("/success", {state: {message: `You, ${auth.userGamer.gamerTag}, successfully GG'd ${gamer.gamerTag}!`}});
            } else {
                setErrors(data);
            }
        })
        .catch(console.log);
    }

    
    const handleRemoveMatch = () => {
        console.log(gamer.gamerId);
        console.log(auth.userGamer.gamerId);
        console.log("removing match!");
        const init = {
            method: 'DELETE'
        };
        fetch(`${url}/match/${gamer.gamerId}/${auth.userGamer.gamerId}`, init)
        .then(response => {
            if (response.status === 204) {
                navigate("/success", {state: {message:  `You, ${auth.userGamer.gamerTag}, took back your GG for ${gamer.gamerTag}.`}});
            } else {
                return Promise.reject(`Unexpected status code: ${response.status}`);
            }
        })
        .catch(console.log);
    }

    return(
        <>
        <main className="container">
            <section className="gamerProfileInfo">
                <h2>{gamer.gamerTag}'s Profile</h2>
                <div>
                    <p>ID: {gamer.gamerId}</p>
                    <p>GENDER: {gamer.genderType}</p>
                    <p>BDAY: {gamer.birthDate}</p>
                    <p>BIO: {gamer.bio}</p>
                    <p>FAV GAMES:</p>
                    <ul>
                        {(gamer.games.length > 0) ? (gamer.games.map(game => 
                            <li key={game.game.gameId}>{game.game.gameTitle}</li>
                        )) : (
                            <p>None yet! Edit your profile to add some!</p>
                        )}
                    </ul>
                    <p>SENT GG's FOR:</p>
                    <ul>
                        {gamer.sentMatches.map(match => 
                            <li key={match.gamerReceiver.gamerId}>{match.gamerReceiver.gamerTag} at {match.dateMatchSent}</li>
                        )}
                    </ul>
                    <p>GOT GG'd BY:</p>
                    <ul>
                        {gamer.receivedMatches.map(match => 
                            <li key={match.gamerSender.gamerId}>{match.gamerSender.gamerTag} at {match.dateMatchReceived}</li>
                        )}
                    </ul>
                    <ul><FindPostsByGamer currentGamerTag={gamer.gamerTag}/></ul>
                </div>
                <div>
                    <p>TODO: make this link only appear if this is YOUR profile</p>
                    {auth.userGamer.gamerId === gamer.gamerId ? 
                        (<Link to={`/profile/${gamer.gamerId}/form`} className="btn btn-success mr-2" type="button">
                            Edit Profile
                        </Link>) : ("CAN'T EDIT PROFILE")}
                        {auth.userGamer.gamerId === gamer.gamerId ? 
                        (<Link to={`/profile/game`} className="btn btn-success mr-2" type="button">
                            Edit Fav Games
                        </Link>) : ("CAN'T EDIT FAV GAMES")}
                    <p>TODO: make this button only appear if this is someone ELSE'S profile</p>
                    {(auth.userGamer.gamerTag) ? (
                        (auth.userGamer.gamerId !== gamer.gamerId) ? (
                        <div>
                            <button onClick={handleAddMatch}>Send a GG!</button>
                            <button onClick={handleRemoveMatch}>Remove GG</button>
                        </div>) : (
                        "You can't give yourself a GG, sorry.")
                        ) : (
                        <div>
                            <p>create a profile to send a GG!</p>
                            <Link to="/profile/form">Create a Profile</Link>
                        </div>)
                    }

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

                    <p><Link to="/gamers">Gamers List</Link></p>
                </div>
            </section>
        </main>
        </>
    );
}

export default GamerProfile;