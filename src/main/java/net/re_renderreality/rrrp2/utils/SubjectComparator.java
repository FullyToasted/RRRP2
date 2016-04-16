package net.re_renderreality.rrrp2.utils;

import org.spongepowered.api.service.permission.Subject;

import java.util.Comparator;

public class SubjectComparator implements Comparator<Subject>
{
	@Override
	public int compare(Subject o1, Subject o2)
	{
		if (o1.getParents().size() > o2.getParents().size())
		{
			return 1;
		}
		else if (o1.getParents().size() == o2.getParents().size())
		{
			return 0;
		}
		else
		{
			return -1;
		}
	}
}