import { Link } from 'react-router-dom';

function About() {
    return(<section className='aboutPage'>
        <header>
            <h1>Welcome to Gamer's Guild</h1>
        </header>
        <div className="about">
            <h2 className='aboutGg'>About GG</h2>
            <div className='three'>
                <div className="aboutLeft">
                    <img src="https://images.unsplash.com/photo-1586182987320-4f376d39d787?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=687&q=80" alt="a hand holding a playstation controller"/>
                </div>
                <div className='aboutText'>
                    <p className="p1">In dapibus ac magna nec ornare. Nam justo ante, faucibus quis sapien non, congue eleifend diam. Proin eget justo ac tortor aliquam finibus. In sapien elit, vestibulum a odio non, ultricies laoreet nisi. Pellentesque mattis lorem vel tristique mollis. Suspendisse ac bibendum mi. Maecenas accumsan lobortis elit ut condimentum. Sed feugiat eu purus a luctus. Fusce ac lobortis justo, eget finibus nisi. Duis dictum est vel bibendum pharetra. Nullam sollicitudin, lacus a imperdiet sollicitudin, tortor sem scelerisque nunc, ut blandit lorem nunc nec nisl. Vivamus ornare hendrerit mi at imperdiet. Phasellus sem sapien, vestibulum vel porta et, dapibus sit amet arcu. Phasellus sed ipsum eu ex interdum ultrices. In dapibus ac magna nec ornare. Nam justo ante, faucibus quis sapien non, congue eleifend diam. Proin eget justo ac tortor aliquam finibus. In sapien elit, vestibulum a odio non, ultricies laoreet nisi. Pellentesque mattis lorem vel tristique mollis. Suspendisse ac bibendum mi. Maecenas accumsan lobortis elit ut condimentum. Sed feugiat eu purus a luctus. Fusce ac lobortis justo, eget finibus nisi. Duis dictum est vel bibendum pharetra. Nullam sollicitudin, lacus a imperdiet sollicitudin, tortor sem scelerisque nunc, ut blandit lorem nunc nec nisl. Vivamus ornare hendrerit mi at imperdiet. Phasellus sem sapien, vestibulum vel porta et, dapibus sit amet arcu. Phasellus sed ipsum eu ex interdum ultrices.</p>
                </div>
                <div className="aboutRight">
                    <img src="https://images.unsplash.com/photo-1581351123004-757df051db8e?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=687&q=80" alt="spy cat"/>
                </div>
            </div>
            <Link to={'/sign_up'}><button className='button aboutButton'>Join us!</button></Link>
        </div>
    </section>);
}

export default About;