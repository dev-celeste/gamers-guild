import { Link } from 'react-router-dom';
function Faq(){
    return(<>
    <div className="container">
       <header>
        <div>
            <h1>Frequently Asked Questions</h1>
        </div>
       </header>
       <section className="Faq">

       <div className="questions">

        <div className="q1">
        <h5>Do I need an account?</h5>
        <p>You do not necessarily need an account to use Gamers' Guild but the amount you can do is quite limited. Without an account you can view our community forum,
            but you will not be able to create or accept posts. With an account you will be able to do all that, as well as have access to our Duo Finder!
            If you would like to create an account now you can click <Link to={'/register'}>here</Link> or on the sign up button above!</p>
        </div>

        <div className='q2'>
            <h5>How old do I have to be to use Gamers' Guild?</h5>
            <p>You must be 16 years or older to create an account and use our Community Forum and Duo Finder,
                 but you are free to view our entire website to your hearts content regardless of age!</p>
        </div>

        <div className='q3'>
            <h5>What consoles/platforms is Gamers' Guild catered towards?</h5>
            <p>We cater towards all consoles and platforms. Whether you're on Playstation, Xbox, Switch, PC, or anything in between, 
                you're sure to find people to game with and hang out!</p>
        </div>

        <div className='q4'>
            <h5>Is there a limit to how many posts I can make in the Community Forum?</h5>
            <p>We do not have a set limit on the number of posts a user can make, however, 
                if we notice a user abusing the system or spamming posts we may ban the account for a period of time</p>
        </div>

        <div className='q5'>
            <h5>How does the Duo Finder Work?</h5>
            <p>Once you have created your account, you can head over to the Duo tab above and begin swiping on potential gaming friends. 
                You will be able to see other users' Gamertag, favorite video games, and bio to see if they're someone you might vibe with!
                If you both like eachother, you both will be matched up and can begin chatting and set up a game to play together.</p>
        </div>

        <div className='q6'>
            <h5>What do I do if another user is harassing me?</h5>
            <p>If you feel another user is being inappropriate or harassing you or other users, you can send a message to us in 
                our <Link to={'/contact'}>contact us</Link> page and we will look into the situation right away and take the appropriate actions.</p>
        </div>

        <div className='q7'>
            <h5>How do I appeal a ban?</h5>
            <p>If you feel your account have been wrongfully banned, you can make an appeal in our <Link to={'/contact'}>contact us</Link> page and we will get back to you as soon as possible!</p>
        </div>

        <div className='q8'>
            <h5>What are your favorite games?</h5>
            <p>Our favorite games are League of Legends, Zero Escape: The Nonary Games, and Overwatch! You might even find us on the Duo Finder or Community Forum, so we can play games together!</p>
        </div>

       </div>
       </section>
       <footer>
        <p>Copyright 2023</p>
       </footer>
    </div>
    </>)
}

export default Faq;