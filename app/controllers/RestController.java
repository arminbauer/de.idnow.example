package controllers;

import static play.libs.Json.toJson;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import models.Company;
import models.Identification;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class RestController extends Controller
{

  @SuppressWarnings("unchecked")
  @Transactional
  /**
   * Start identification action from routes
   * 
   * @return
   */
  public Result startIdentification()
  {
    Identification identification;
    JsonNode json = request().body().asJson();

    if (json == null)
    {
      identification = Form.form(Identification.class).bindFromRequest().get();

      // Check if identification with same id already exists
      List<Identification> identifications = (List<Identification>)JPA.em().createQuery("select i from Identification i where id like " + identification.id)
        .getResultList();
      if (identifications.isEmpty())
      {
        JPA.em().persist(identification);
        return redirect(routes.Application.index());
      }
      else
      {
        return badRequest("Identification with same id already exists");
      }
    }
    else
    {
      identification = Json.fromJson(json, Identification.class);
      
      if (identification == null)
      {
        return badRequest("Expecting Json data");
      }
      
      // Check if identification with same id already exists
      List<Identification> identifications = (List<Identification>)JPA.em().createQuery("select i from Identification i where id like " + identification.id)
        .getResultList();
      if (identifications.isEmpty())
      {
        JPA.em().persist(identification);
        return ok();
      }
      else
      {
        return badRequest("Identification with same id already exists");
      }
    }
  }

  @SuppressWarnings("unchecked")
  @Transactional
  /**
   * Add company action from routes
   * 
   * @return
   */
  public Result addCompany()
  {
    JsonNode json = request().body().asJson();

    if (json == null)
    {
      return badRequest("Expecting Json data");
    }
    else
    {
      Company company = Json.fromJson(json, Company.class);
      // Check if company with same id already exists
      List<Company> companies = (List<Company>)JPA.em().createQuery("select c from Company c where id like " + company.id).getResultList();
      if (companies.isEmpty())
      {
        JPA.em().persist(company);
        return ok();
      }
      else
      {
        return badRequest("Company with same id already exists");
      }
    }
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  /**
   * Pending Identifications action from routes
   * 
   * @return
   */
  public Result pendingIdentifications()
  {
    List<Company> companies = (List<Company>)JPA.em().createQuery("select c from Company c").getResultList();
    List<Identification> identifications = (List<Identification>)JPA.em().createQuery("select i from Identification i").getResultList();

    List<Identification> sortedIdentifications = bubblesortIdentifications(identifications, companies);

    return ok(toJson(sortedIdentifications));
  }

  /**
   * Bubble sort list by priority
   * 
   * @param identifications
   * @param companies
   * @return
   */
  public List<Identification> bubblesortIdentifications(List<Identification> identifications, List<Company> companies)
  {
    Identification temp;

    for (int i = 1; i < identifications.size(); i++)
    {
      for (int j = 0; j < identifications.size() - i; j++)
      {
        if (compareIdentifications(identifications.get(j), identifications.get(j + 1), companies))
        {
          temp = identifications.get(j);
          identifications.remove(j);
          identifications.add(j + 1, temp);
        }
      }
    }
    return identifications;
  }

  /**
   * Returns false, if 1st identification has higher prio than 2nd identification
   * 
   * @param ident1
   * @param ident2
   * @param companies
   */
  private boolean compareIdentifications(Identification ident1, Identification ident2, List<Company> companies)
  {
    Company company1 = getCompanyById(ident1.companyid);
    Company company2 = getCompanyById(ident2.companyid);
    
    if(company1 == null || company2 == null)
    {
      return false;
    }

    if (company1.equals(company2))
    {
      if (ident1.waiting_time < ident2.waiting_time)
      {
        return true;
      }
      else
      {
        return false;
      }
    }
    else
    {
      if (company1.current_sla_percentage == company2.current_sla_percentage)
      {
        if (company1.sla_time > company2.sla_time)
        {
          return true;
        }
        else
        {
          return false;
        }
      }
      else
      {
        if (company1.current_sla_percentage > company2.current_sla_percentage)
        {
          return true;
        }
        else
        {
          return false;
        }
      }
    }
  }

  /**
   * Returns company by given id
   * 
   * @param id
   * @return
   */
  @SuppressWarnings("unchecked")
  private Company getCompanyById(int id)
  {
    List<Company> companies = (List<Company>)JPA.em().createQuery("select c from Company c where id like " + id).getResultList();

    if (companies.isEmpty())
    {
      return null;
    }
    else
    {
      return companies.get(0);
    }
  }

}
