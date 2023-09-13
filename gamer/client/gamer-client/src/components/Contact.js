function Contact(){
    return(<>
    <div className="container">
       <header>
        <div>
            <h1>Contact Us</h1>
        </div>
       </header>
       <section className="contact">

        <div className="contactUsForm">
        <h2>Have a question?<br/>Shoot us message!</h2>
            <form action="mailto:recipient@example.com" method="get" enctype="text/plain">      
                <input name="name" type="text" className="feedback-input" placeholder="GamerTag" />   
                <input name="email" type="text" className="feedback-input" placeholder="Email" />
                <textarea name="text" className="feedback-input" placeholder="Comment"></textarea>
                <input type="submit" value="SUBMIT"/>
            </form>
        </div>

        <div className="developerContact">
            <h5>Website Developers</h5>
            <p>Maria Alcantara<br>
            </br>Jackie Luu<br>
            </br>Jay Wu</p>
        </div>
        
        <div className="apiContact">
            <h5>API</h5>
            <p>Link: TBA</p>
        </div>
       </section>
       <footer>
        <p>Copyright 2023</p>
       </footer>
    </div>
    </>)
}



export default Contact;