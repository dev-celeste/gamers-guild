import React from "react";
import { useEffect, useState } from "react";
import FindGamer from "./FindGamer";
import FindGameTitle from "./FindGameTitle";
import { Link } from 'react-router-dom';

const SpecificPosts = (props) => {
    const [posts, setPosts] = useState([]);
    const post_url = 'http://localhost:8080/posting'


    useEffect(() => {
        fetch(`${post_url}/game/id/${props.currentGame}`)
            .then(response => {
                if (response.status === 200) {
                    return response.json();
                } else {
                    return Promise.reject(`Unexpected status code: ${response.status}`);
                }
            })
            .then(data => setPosts(data)) // here we are setting our data to our state variable 
            .catch(console.log);
    }, []); // empty dependency array tells react to run once when the component is intially loaded




    return(
        
        <div className="post-preview">
            {posts.map((post, index) => (
                <Link to={`/post/${post.postingId}`}><div key={index}>
                    <h3 className="postTitle">{post.header}</h3>
                    <p className="postDate">{post.datePosted}</p>
                    <FindGameTitle currentGameId={post.gameId}/>
                    <FindGamer currentGamerId={post.gamerId}/>
                </div></Link>
            ))}
        </div>
    );
}

export default SpecificPosts;