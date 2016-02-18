package actors;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import com.google.inject.Injector;

/**
 * injector for actor
 * see {@see http://stackoverflow.com/a/17684483/755183}
 *
 * Created by bduisenov on 18/02/16.
 */
public class GuiceInjectedActor implements IndirectActorProducer {

    final Injector injector;

    final Class<? extends Actor> actorClass;

    public GuiceInjectedActor(Injector injector, Class<? extends Actor> actorClass) {
        this.injector = injector;
        this.actorClass = actorClass;
    }

    @Override
    public Class<? extends Actor> actorClass() {
        return actorClass;
    }

    @Override
    public Actor produce() {
        return injector.getInstance(actorClass);
    }

}

