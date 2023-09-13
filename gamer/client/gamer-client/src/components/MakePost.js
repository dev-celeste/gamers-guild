import React, { useContext } from "react";
import { useEffect, useState } from "react";
import { useParams} from 'react-router-dom';
import { Link, useNavigate } from 'react-router-dom';
import AuthContext from "../context/AuthContext";


const today = new Date();
const adjustedTodayForTimezome = new Date(today.getTime() - today.getTimezoneOffset() * 60000);

const POST_DEFAULT = {
    header: '',
    description: '',
    datePosted: adjustedTodayForTimezome.toISOString().split("T")[0],
    gamerId: '',
    gameId: ''
}

const MakePost = () => {
    const [errors, setErrors] = useState([]);
    const [post, setPost] = useState(POST_DEFAULT);
    const post_url = 'http://localhost:8080/posting'
    const { id } = useParams();
    const navigate = useNavigate();
    const auth = useContext(AuthContext);
    
    useEffect(() => {
        console.log(id);
        if(id) {
            // if id exists, load in post content!
            fetch(`${post_url}/${id}`)
            .then(response => {
                if (response.status === 200) {
                    return response.json();
                } else {
                    return Promise.reject(`Unexpected status code: ${response.status}`);
                }
            })
            .then(data => {
                console.log(data);
                setPost(data);
                console.log(post);
                if (auth.userGamer.gamerId != data.gamerId) {
                    console.log("You're trying to edit a post that's not yours!");
                    navigate("/error", {state: {message: "You're trying to edit a post that isn't yours!"}});    
                }
            }) // here we are setting our data to our state variable 
            .catch(console.log);
        } else if (auth.userGamer.gamerTag) {
            POST_DEFAULT.gamerId = auth.userGamer.gamerId;
        } 
    }, []); 


    // games fetch
    const [games, setGames] = useState([]);
    const game_url = 'http://localhost:8080/game'

    useEffect(() => {
        fetch(game_url)
        .then(response => {
            if (response.status === 200) {
                return response.json();
            } else {
                return Promise.reject(`Unexpected status code: ${response.status}`);
            }
        })
        .then(data => setGames(data)) // here we are setting our data to our state variable 
        .catch(console.log);
    
    }, []);

    const handlePostChange = (event) => {
        // make a copy of the object 
        const newPost = { ...post };
        console.log(newPost);
        // update the value of the property changed 
        newPost[event.target.name] = event.target.value;
        // set the state 
        setPost(newPost);
        console.log(post);
    }

    const handlePostSubmit = (event) => {
        console.log(id);
        event.preventDefault();
        if (id) {
            updatePost();
        }else {
            addPost();
        }
        
    }

    const addPost = () => {
        const init = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(post)
        };
        fetch(post_url, init)
        .then(response => {
            if(response.status === 201 || response.status === 400){
                return response.json();
                
            }else{
                return Promise.reject(`Unexpected status code: ${response.status}`);
            }
        })
        .then(data =>{
            console.log(data);
            if(data.postingId){
                navigate('/community');
            }else{
                setErrors(data);
            }
        })
        .catch(console.log)
    }

    const updatePost = () => {
        post.postingId = id;
        const init = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },   
            body: JSON.stringify(post)
        }

        fetch(`${post_url}/${id}`, init)
        .then(response => {
            if(response.status === 204){
                return null;
            }else if(response.status === 400){
                return response.json();
            }
            else {
                return Promise.reject(`Unexpected status code: ${response.status}`);
            }
        })
        .then(data =>{
            if (!data) {
                // send the user back to the list route
                navigate('/community')
            } else {
                setErrors(data);
            }
        })
        .catch(console.log);
    };
    
    console.log(post);

    return(
            <section className="make-post">
                <h2 className="postFormHeading" id="postFormHeading">{id > 0 ? 'Update Post' : 'Add Post'}</h2>
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
                <form onSubmit={handlePostSubmit} id="postForm">
                    <input id="dateRequired" 
                        type="date" 
                        name="dateRequired"
                        readOnly={true} 
                        value={post.datePosted}/>
                    <input id="gamerId" 
                        type="text" 
                        name="gamerId"
                        readOnly={true} 
                        value={auth.userGamer.gamerId}
                        defaultValue={auth.userGamer.gamerId}/>
                    <fieldset className="form-group">
                        <label htmlFor="header">Subject:</label>
                        <input id="header" 
                            name="header" 
                            type="text" 
                            className="form-control"
                            value={post.header}
                            onChange={handlePostChange}/>
                    </fieldset>
                    <fieldset className="form-group">
                        <label htmlFor="description">Description:</label>
                        <input id="description" 
                            name="description" 
                            type="text" 
                            className="form-control"
                            value={post.description}
                            onChange={handlePostChange}/>
                    </fieldset>
                    <fieldset className="form-group">
                        <label htmlFor="gameId">Game:</label>
                        <input id="gameId" 
                            name="gameId" 
                            type="search"
                            placeholder="Search for a game…" 
                            className="form-control"
                            list="games"
                            value={post.gameId}
                            onChange={handlePostChange}/>
                    </fieldset>
                    <datalist id="games">
                        {games.map((game) => (
                            <option key={game.gameId} 
                            value={game.gameId}>{game.gameTitle}</option>
                        ))}
                    </datalist>
                    <div>
                        <p>Don't see your game in the results? Go to Games List and add it!</p>
                        <Link to="/game">Games List</Link>
                    </div>
                    <div className="mt-4">
                        <button className="btn btn-success submitForm" type="submit" id="postFormSubmitButton">
                            {id > 0 ? 'Update Post' : 'Add Post'}
                        </button>
                        <Link to={"/community"} className="btn btn-danger cancelSubmit" type="button">
                            Cancel
                        </Link>
                </div>
                </form>
            </section>
    );
}

export default MakePost;