CREATE FUNCTION [dbo].[BCrypt](@password [nvarchar](4000), @rounds [int])
    RETURNS [nvarchar](4000) WITH EXECUTE AS CALLER
AS
    EXTERNAL NAME [BCrypt].[BCryptPackage.UserDefinedFunctions].[BCrypt]
GO
EXEC sys.sp_addextendedproperty @name=N'AutoDeployed', @value=N'yes' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'FUNCTION',@level1name=N'BCrypt'
GO
EXEC sys.sp_addextendedproperty @name=N'SqlAssemblyFile', @value=N'BCryptAssembly' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'FUNCTION',@level1name=N'BCrypt'
GO
EXEC sys.sp_addextendedproperty @name=N'SqlAssemblyFileLine', @value=813 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'FUNCTION',@level1name=N'BCrypt'
GO
