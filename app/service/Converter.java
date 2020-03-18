package service;

public interface Converter<Domain, Dto> {

	Dto convertBack(Domain domain);

	Domain convert(Dto dto);
}
